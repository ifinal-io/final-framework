package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 18:33:19
 * @since 1.0
 */
public class SqlOrderFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlOrderFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_ORDER;
    }

    @Override
    public Element buildSqlFragment(Document document, Entity entity) {
        /**
         *     <sql id="sql-order">
         *         <if test="sort != null">
         *             <trim prefix="ORDER BY">
         *                 <foreach collection="sort" item="order" separator=",">
         *                     ${order.property.column} ${order.direction.name()}
         *                 </foreach>
         *             </trim>
         *         </if>
         *     </sql>
         */
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        Element ifSortNotNull = ifTest(document, "sort != null");

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "ORDER BY");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "sort");
        foreach.setAttribute("item", "order");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNode(document, "${order.property.column} ${order.direction.value}"));

        trim.appendChild(foreach);

        ifSortNotNull.appendChild(trim);

        sql.appendChild(ifSortNotNull);

        return sql;
    }
}
