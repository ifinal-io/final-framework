package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-13 12:15:40
 * @since 1.0
 */
public class SqlWhereIdFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_WHERE_ID;
    }


    /**
     * <pre>
     *     <code>
     *          <sql id="sql-where-id">
     *              <where>
     *                  <trim prefix="id =">#{id}</trim>
     *              </where>
     *          </sql>
     *     </code>
     * </pre>
     *
     * @param document
     * @param entity
     * @return
     */
    @Override
    public Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        Element where = document.createElement("where");
        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", String.format("%s =", Utils.getInstance().formatPropertyColumn(null, entity.getRequiredIdProperty())));
        trim.appendChild(textNode(document, "#{id}"));
        where.appendChild(trim);
        sql.appendChild(where);
        return sql;
    }
}
