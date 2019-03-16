package org.finalframework.mybatis.builder;


import org.finalframework.data.mapping.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-09 18:15:25
 * @since 1.0
 */
public class SelectCountMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && method.getName().equals("selectCount");
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        /*
         *     <select id="selectCount" resultType="java.lang.Long">
         *         SELECT COUNT(*) FROM
         *         <include refid="sql-table"/>
         *         <where>
         *
         *     </select>
         */
        final Element selectCount = document.createElement("select");
        selectCount.setAttribute("id", method.getName());
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
