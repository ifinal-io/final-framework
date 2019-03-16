package org.finalframework.mybatis.builder;


import org.finalframework.data.mapping.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 19:34:42
 * @since 1.0
 */
public class FinalXmlMapperBuilder implements XmlMapperBuilder {
    private ResultMapXmlMapperBuilder resultMapXmlMapperBuilder = new DefaultResultMapXmlMapperBuilder();
    private List<MethodXmlMapperBuilder> methodXmlMapperBuilders = new ArrayList<>(8);

    {
        methodXmlMapperBuilders.add(new CommonMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new InsertMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new UpdateMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new DeleteMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectCountMethodXmlMapperBuilder());
    }

    @Override
    public Collection<Element> build(Document document, Class<?> mapper, Entity<?> entity) {
        final Collection<Element> elements = new ArrayList<>(32);
        final Collection<Element> sqlFragments = new ArrayList<>(16);
        final Set<MethodXmlMapperBuilder> supports = new LinkedHashSet<>();
        // resultMap
        elements.add(resultMapXmlMapperBuilder.build(document, entity));

        Arrays.stream(mapper.getMethods())
                .filter(it -> !it.isDefault())
                .forEach(method -> {
                    for (MethodXmlMapperBuilder methodXmlMapperBuilder : methodXmlMapperBuilders) {
                        if (methodXmlMapperBuilder.supports(method)) {
                            final Element element = methodXmlMapperBuilder.build(method, document, entity);
                            if (element != null) {
                                elements.add(element);
                            }
                            if (!supports.contains(methodXmlMapperBuilder)) {
                                supports.add(methodXmlMapperBuilder);
                                final Collection<Element> fragments = methodXmlMapperBuilder.sqlFragments(method, document, entity);
                                if (fragments != null && !fragments.isEmpty()) {
                                    sqlFragments.addAll(fragments);
                                }
                            }
                        }
                    }
                });

        elements.addAll(sqlFragments);
        return elements;
    }


}
