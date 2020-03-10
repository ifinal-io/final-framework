package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:17:25
 * @since 1.0
 */
public class SelectCountMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder {
    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectCount".equals(method.getSimpleName().toString());
    }


    @Override
    public Element buildMethodElement(ExecutableElement method, Document document, Entity<Property> entity) {
        final Element selectCount = document.createElement("select");
        selectCount.setAttribute("id", method.getSimpleName().toString());
        selectCount.setAttribute("resultType", Long.class.getCanonicalName());

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "SELECT COUNT(*) FROM");
        // <include refid="sql-table"/>
        trim.appendChild(include(document, SQL_TABLE));
        trim.appendChild(where(document,
                whenIdsNotNull(document, entity),
                whenQueryNotNull(document)
        ));
//        trim.appendChild(include(document, SQL_QUERY));
        selectCount.appendChild(trim);
        return selectCount;
    }
}
