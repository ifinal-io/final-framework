package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <pre>
 *     <code>
 *          <sql id="sql-criteria">
 *              <foreach collection="criteria" item="item" separator="AND">
 *                  ${item.sql}
 *              </foreach>
 *          </sql>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-14 10:48:15
 * @since 1.0
 */
public class SqlCriteriaFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_CRITERIA;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criteria");
        foreach.setAttribute("item", "item");
        foreach.setAttribute("separator", "criteria");
        foreach.appendChild(textNode(document, "${item.sql}"));
        sql.appendChild(foreach);
        return sql;
    }
}
