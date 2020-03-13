package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-11 18:35:32
 * @since 1.0
 */
public class SqlLimitFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlLimitFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_LIMIT;
    }

    @Override
    public Element buildSqlFragment(Document document, Entity<Property> entity) {
        /**
         *      <sql id="sql-limit">
         *         <if test="limit != null">
         *             <trim prefix="LIMIT">
         *                 <if test="limit.offset != null">
         *                     #{limit.offset},
         *                 </if>
         *                 <if test="limit.limit != null">
         *                     #{limit.limit}
         *                 </if>
         *             </trim>
         *         </if>
         *     </sql>
         */

        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());
        Element ifLimitNotNull = ifTest(document, "limit != null");

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "LIMIT");

        Element offsetNotNull = ifTest(document, "limit.offset != null");
        offsetNotNull.appendChild(textNode(document, "#{limit.offset},"));

        Element limitNotNull = ifTest(document, "limit.limit != null");
        limitNotNull.appendChild(textNode(document, "#{limit.limit}"));

        trim.appendChild(offsetNotNull);
        trim.appendChild(limitNotNull);

        ifLimitNotNull.appendChild(trim);

        sql.appendChild(ifLimitNotNull);

        return sql;
    }
}
