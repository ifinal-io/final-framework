package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:15:36
 * @since 1.0
 */
public class SelectIdsMethodXmlMapperBuilder extends AbsXmlMapperBuilder implements MethodXmlMapperBuilder {
    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectIds".equals(method.getSimpleName().toString());
    }

    @Override
    public Element build(ExecutableElement method, Document document, Entity<Property> entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getSimpleName().toString());
        final Property idProperty = entity.getRequiredIdProperty();
        select.setAttribute("resultType", idProperty.getMetaTypeElement().getQualifiedName().toString());
        select.appendChild(textNode(document, "SELECT "));
        select.appendChild(textNode(document, idProperty.getColumn()));
        select.appendChild(textNode(document, " FROM "));
        select.appendChild(include(document, SQL_TABLE));
        select.appendChild(where(document, whenQueryNotNull(document)));
        select.appendChild(include(document, SQL_ORDER));
        select.appendChild(include(document, SQL_LIMIT));
        return select;
    }
}
