package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:15:36
 * @since 1.0
 */
public class SelectIdsMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {
    public SelectIdsMethodXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectIds".equals(method.getSimpleName().toString());
    }

    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getSimpleName().toString());
        final Property idProperty = entity.getRequiredIdProperty();
        select.setAttribute("resultType", idProperty.getJavaTypeElement().getQualifiedName().toString());

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", String.format("SELECT %s FROM", idProperty.getColumn()));

        trim.appendChild(include(document, SQL_TABLES));
        trim.appendChild(include(document, SQL_QUERY));
        select.appendChild(trim);
        return select;
    }
}
