package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.core.Assert;
import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 15:24:42
 * @since 1.0
 */
public class FinalXmlMapperBuilder implements XmlMapperBuilder {

    private List<ResultMapXmlMapperBuilder> resultMapXmlMapperBuilders = new ArrayList<>(1);
    private List<MethodXmlMapperBuilder> methodXmlMapperBuilders = new ArrayList<>(8);
    private List<SqlXmlMapperBuilder> sqlXmlMapperBuilders = new ArrayList<>();

    {
        resultMapXmlMapperBuilders.add(new DefaultResultMapXmlMapperBuilder());

        methodXmlMapperBuilders.add(new InsertMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new UpdateMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new DeleteMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectIdsMethodXmlMapperBuilder());
        methodXmlMapperBuilders.add(new SelectCountMethodXmlMapperBuilder());

        sqlXmlMapperBuilders.add(new SqlTableXmlMapperBuilder());
        sqlXmlMapperBuilders.add(new SqlOrderXmlMapperBuilder());
        sqlXmlMapperBuilders.add(new SqlLimitXmlMapperBuilder());
        sqlXmlMapperBuilders.add(new SqlSelectColumnsXmlMapperBuilder());

    }

    @Override
    public Collection<Element> build(Document document, TypeElement mapper, Entity<Property> entity) {
        final Collection<Element> resultMapElements = new ArrayList<>(8);
        final Collection<Element> methodElements = new ArrayList<>(8);
        final Collection<Element> methodSqlFragmentElements = new ArrayList<>(16);
        final Collection<Element> sqlFragmentElements = new ArrayList<>(16);

        resultMapXmlMapperBuilders.forEach(item -> resultMapElements.add(item.build(document, entity)));
        sqlXmlMapperBuilders.forEach(item -> sqlFragmentElements.add(item.build(document, entity)));

        mapper.getEnclosedElements()
                .stream()
                .filter(it -> it.getKind() == ElementKind.METHOD)
                .map(it -> (ExecutableElement) it)
                .filter(it -> !it.isDefault())
                .forEach(method -> {
                    for (MethodXmlMapperBuilder methodXmlMapperBuilder : methodXmlMapperBuilders) {
                        if (methodXmlMapperBuilder.supports(method)) {
                            methodElements.add(methodXmlMapperBuilder.build(method, document, entity));
                            Collection<Element> sqlFragments = methodXmlMapperBuilder.sqlFragments(method, document, entity);
                            if (Assert.nonEmpty(sqlFragments)) {
                                methodSqlFragmentElements.addAll(sqlFragments);
                            }
                        }
                    }
                });

        final Collection<Element> elements = new ArrayList<>(32);
        elements.addAll(resultMapElements);
        elements.addAll(methodElements);
        elements.addAll(methodSqlFragmentElements);
        elements.addAll(sqlFragmentElements);
        return elements;
    }
}
