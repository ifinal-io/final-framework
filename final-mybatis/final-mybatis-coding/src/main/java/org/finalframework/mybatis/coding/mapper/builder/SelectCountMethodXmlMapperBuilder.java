package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.lang.model.element.ExecutableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-12 11:17:25
 * @since 1.0
 */
public class SelectCountMethodXmlMapperBuilder extends AbsXmlMapperBuilder implements MethodXmlMapperBuilder {
    @Override
    public boolean supports(ExecutableElement method) {
        return !method.isDefault() && "selectCount".equals(method.getSimpleName().toString());
    }

    @Override
    public Element build(ExecutableElement method, Document document, Entity<Property> entity) {
        /*
         *     <select id="selectCount" resultType="java.lang.Long">
         *         SELECT COUNT(*) FROM
         *         <include refid="sql-table"/>
         *         <where>
         *
         *     </select>
         */
        final Element selectCount = document.createElement("select");
        selectCount.setAttribute("id", method.getSimpleName().toString());
        selectCount.setAttribute("resultType", Long.class.getCanonicalName());
        Text selectCountText = document.createTextNode("\n\t\tSELECT COUNT(*) FROM\n");
        selectCount.appendChild(selectCountText);
        // <include refid="sql-table"/>
        selectCount.appendChild(include(document, SQL_TABLE));
        selectCount.appendChild(where(document, whenQueryNotNull(document)));
        selectCount.appendChild(include(document, SQL_ORDER));
        selectCount.appendChild(include(document, SQL_LIMIT));
        return selectCount;
    }
}
