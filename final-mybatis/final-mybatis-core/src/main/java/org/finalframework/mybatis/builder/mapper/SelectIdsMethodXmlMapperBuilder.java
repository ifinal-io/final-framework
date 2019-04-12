package org.finalframework.mybatis.builder.mapper;


import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.lang.reflect.Method;

/**
 * <pre>
 *     <select id="select" result="ResultMap">
 *          SELECT
 *              id
 *          FROM
 *          <include id="sql-table"/>
 *          <where>
 *             <choose>
 *                 <when test="query != null and query.criteria != null">${query.criteria.sql}</when>
 *             </choose>
 *          </where>
 *          <include id="sql-order"/>
 *          <include id="sql-limit"/>
 *     </select>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-09 17:50:55
 * @since 1.0
 */
public class SelectIdsMethodXmlMapperBuilder extends AbsMethodXmlMapperBuilder implements MethodXmlMapperBuilder {

    private static final String METHOD_SELECT_IDS = "selectIds";

    @Override
    public boolean supports(Method method) {
        return !method.isDefault() && METHOD_SELECT_IDS.equals(method.getName());
    }

    @Override
    public Element build(Method method, Document document, Entity<?> entity) {
        final Element select = document.createElement("select");
        select.setAttribute("id", method.getName());
        final Property idProperty = entity.getRequiredIdProperty();
        select.setAttribute("resultType", idProperty.getType().getCanonicalName());
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
