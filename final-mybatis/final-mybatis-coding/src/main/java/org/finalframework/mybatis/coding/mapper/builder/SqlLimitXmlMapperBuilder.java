package org.finalframework.mybatis.coding.mapper.builder;

import org.finalframework.data.coding.entity.Entity;
import org.finalframework.data.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 18:35:32
 * @since 1.0
 */
public class SqlLimitXmlMapperBuilder extends AbsXmlMapperBuilder implements SqlXmlMapperBuilder {

    @Override
    public String id() {
        return SQL_LIMIT;
    }

    @Override
    public Element build(Document document, Entity<Property> entity) {
        /**
         * <sql id="sql-limit">
         *     <choose>
         *         <when test="query != null and query.offset != null and  query.limit != null">
         *             LIMIT #{query.offset},#{query.limit}
         *         </when>
         *         <when test="query != null and query.limit != null">
         *             LIMIT #{query.limit}
         *         </when>
         *     </choose>
         * </sql>
         */

        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        final Element whenOffsetLimitNotNull = whenOrOtherwise(document, "query != null and query.offset != null and query.limit != null", textNode(document, " LIMIT #{query.offset},#{query.limit}"));
        final Element whenLimitNotNull = whenOrOtherwise(document, "query != null and query.limit != null", textNode(document, " LIMIT #{query.limit}"));
        sql.appendChild(choose(document, Arrays.asList(whenOffsetLimitNotNull, whenLimitNotNull)));
        return sql;
    }
}
