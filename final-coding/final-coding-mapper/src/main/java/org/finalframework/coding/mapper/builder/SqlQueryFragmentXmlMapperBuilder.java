package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/query">query</a>
 * <pre>
 *     <code>
 *          <sql id="sql-query">
 *              <if test="query != null">
 *                  <bind name="criteria" value="query.criteria"/>
 *                  <bind name="sort" value="query.sort"/>
 *                  <bind name="limit" value="query.limit"/>
 *                  <where>
 *                      <include refid="sql-criteria"/>
 *                  </where>
 *                  <include refid="sql-group"/>
 *                  <include refid="sql-order"/>
 *                  <include refid="sql-limit"/>
 *              </if>
 *          </sql>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2019-10-13 11:12:07
 * @see SqlOrderFragmentXmlMapperBuilder
 * @see SqlLimitFragmentXmlMapperBuilder
 * @since 1.0
 */
public class SqlQueryFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_QUERY;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {

        final Element sql = sql(document, id());
        Element ifQueryNotNull = ifTest(document, "query != null");

        ifQueryNotNull.appendChild(bind(document, "criteria", "query.criteria"));
        ifQueryNotNull.appendChild(bind(document, "group", "query.group"));
        ifQueryNotNull.appendChild(bind(document, "sort", "query.sort"));
        ifQueryNotNull.appendChild(bind(document, "limit", "query.limit"));
        ifQueryNotNull.appendChild(include(document, SQL_WHERE_CRITERIA));
        ifQueryNotNull.appendChild(include(document, SQL_ORDER));
        ifQueryNotNull.appendChild(include(document, SQL_LIMIT));
        sql.appendChild(ifQueryNotNull);
        return sql;
    }

}
