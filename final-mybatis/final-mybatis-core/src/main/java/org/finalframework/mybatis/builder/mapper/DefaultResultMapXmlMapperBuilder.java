package org.finalframework.mybatis.builder.mapper;


import org.finalframework.core.Assert;
import org.finalframework.data.mapping.Entity;
import org.finalframework.mybatis.xml.element.Association;
import org.finalframework.mybatis.xml.element.Result;
import org.finalframework.mybatis.xml.element.ResultMap;
import org.finalframework.mybatis.xml.element.ResultMapFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 19:37:38
 * @since 1.0
 */
public class DefaultResultMapXmlMapperBuilder implements ResultMapXmlMapperBuilder {
    private ResultMapFactory resultMapFactory;

    public DefaultResultMapXmlMapperBuilder(ResultMapFactory resultMapFactory) {
        this.resultMapFactory = resultMapFactory;
    }

    public DefaultResultMapXmlMapperBuilder() {
        this(new ResultMapFactory());
    }

    @Override
    public Element build(Document document, Entity<?> entity) {
        return buildResultMapElement(document, resultMapFactory.create(entity));
    }

    private Element buildResultMapElement(Document document, ResultMap resultMap) {
        final Element element = document.createElement(resultMap.name());
        element.setAttribute("id", resultMap.getId());
        element.setAttribute("type", resultMap.getType().getCanonicalName());

        resultMap.stream()
                .map(it -> {
                    switch (it.type()) {
                        case RESULT:
                        case ID_RESULT:
                            return buildResultElement(document, (Result) it);
                        case ASSOCIATION:
                            return buildAssociationElement(document, (Association) it);
                        default:
                            throw new IllegalArgumentException("");
                    }
                })
                .forEach(element::appendChild);

        return element;
    }

    private Element buildResultElement(Document document, Result result) {
        final Element element = document.createElement(result.name());
        element.setAttribute("property", result.getProperty());
        element.setAttribute("column", result.getColumn());
        if (Assert.nonNull(result.getJavaType())) {
            element.setAttribute("javaType", result.getJavaType().getCanonicalName());
        }
        if (Assert.nonNull(result.getTypeHandler())) {
            element.setAttribute("typeHandler", result.getTypeHandler().getClass().getCanonicalName());
        }
        return element;
    }

    private Element buildAssociationElement(Document document, Association association) {
        final Element element = document.createElement(association.name());
        element.setAttribute("property", association.getProperty());
        element.setAttribute("javaType", association.getJavaType().getCanonicalName());

        association.stream()
                .map(it -> {
                    switch (it.type()) {
                        case RESULT:
                        case ID_RESULT:
                            return buildResultElement(document, (Result) it);
                        default:
                            throw new IllegalArgumentException("");
                    }
                })
                .forEach(element::appendChild);

        return element;
    }


}
