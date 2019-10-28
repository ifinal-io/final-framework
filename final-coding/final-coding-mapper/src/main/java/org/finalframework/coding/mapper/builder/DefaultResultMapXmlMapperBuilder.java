package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.xml.Association;
import org.finalframework.coding.mapper.xml.Result;
import org.finalframework.coding.mapper.xml.ResultMap;
import org.finalframework.core.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-10 16:33:53
 * @since 1.0
 */
public class DefaultResultMapXmlMapperBuilder implements AbsResultMapXmlMapperBuilder {


    @Override
    public Element buildResultMap(Document document, Entity<Property> entity) {
        return buildResultMap(document, ResultMap.from(entity));
    }

    private Element buildResultMap(Document document, ResultMap resultMap) {
        Element element = document.createElement(resultMap.name());
        element.setAttribute("id", resultMap.getId());
        element.setAttribute("type", resultMap.getType().getQualifiedName().toString());

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

    private Element buildAssociationElement(Document document, Association association) {
        final Element element = document.createElement(association.name());
        element.setAttribute("property", association.getProperty());
        element.setAttribute("javaType", association.getJavaType().getQualifiedName().toString());

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

    private Element buildResultElement(Document document, Result result) {
        final Element element = document.createElement(result.name());
        element.setAttribute("property", result.getProperty());
        element.setAttribute("column", result.getColumn());
        if (Assert.nonNull(result.getJavaType())) {
            element.setAttribute("javaType", result.getJavaType().getQualifiedName().toString());
        }
        if (Assert.nonNull(result.getTypeHandler())) {
            element.setAttribute("typeHandler", result.getTypeHandler().getQualifiedName().toString());
        }
        return element;
    }

}
