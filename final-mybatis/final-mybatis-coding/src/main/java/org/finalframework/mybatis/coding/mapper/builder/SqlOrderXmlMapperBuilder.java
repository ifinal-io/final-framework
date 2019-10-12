package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 18:33:19
 * @since 1.0
 */
public class SqlOrderXmlMapperBuilder extends AbsXmlMapperBuilder implements SqlXmlMapperBuilder {

    @Override
    public String id() {
        return SQL_ORDER;
    }

    @Override
    public Element build(Document document, Entity<Property> entity) {
        /**
         * <sql id="sql-order">
         *     <if test="query != null and query.sort != null and query.sort.size > 0">
         *         ORDER BY
         *         <foreach collection="query.sort" index="index" item="order" separator=",">
         *              #{order}
         *         </foreach>
         *     </if>
         * </sql>
         */
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        final Element ifQueryNotNullAndQuerySortNotNull = document.createElement("if");
        ifQueryNotNullAndQuerySortNotNull.setAttribute("test", "query != null and query.sort != null and query.sort.size > 0");
        ifQueryNotNullAndQuerySortNotNull.appendChild(textNode(document, "ORDER BY ${query.sort.sql}"));
        sql.appendChild(ifQueryNotNullAndQuerySortNotNull);

        return sql;
    }
}
