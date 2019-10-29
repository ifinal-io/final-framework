package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-13 12:15:40
 * @since 1.0
 */
public class SqlWhereIdsFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_WHERE_IDS;
    }


    /**
     * <pre>
     *     <code>
     *          <sql id="sql-where-ids">
     *             <if test="ids != null">
     *                 <trim prefix="id IN">
     *                     <foreach collection="ids" item="id" open="(" separator="," close=")">
     *                         #{id}
     *                     </foreach>
     *                 </trim>
     *             </if>
     *          </sql>
     *     </code>
     * </pre>
     *
     * @param document
     * @param entity
     * @return
     */

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        Element where = document.createElement("where");

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", entity.getRequiredIdProperty().getColumn() + " IN");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "ids");
        foreach.setAttribute("item", "id");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("separator", ",");
        foreach.setAttribute("close", ")");
        foreach.appendChild(textNode(document, "#{id}"));

        trim.appendChild(foreach);

        where.appendChild(trim);

        sql.appendChild(where);
        return sql;
    }
}
