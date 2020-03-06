package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Arrays;

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
public class SqlWhereCriteriaFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    @Override
    public String id() {
        return SQL_WHERE_CRITERIA;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity<Property> entity) {
        Element sql = sql(document, id());
        Element where = document.createElement("where");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criteria");
        foreach.setAttribute("item", "criterion");
        foreach.setAttribute("separator", "AND");
        foreach.appendChild(
                choose(document, Arrays.asList(
                        /**
                         * @see SqlCriteriaFragmentXmlMapperBuilder
                         */
                        whenOrOtherwise(document, "criterion.chain", include(document, SQL_CRITERIA)),
                        /**
                         * @see SqlCriterionFragmentXmlMapperBuilder
                         */
                        whenOrOtherwise(document, null, include(document, SQL_CRITERION))
                ))
        );

        where.appendChild(foreach);
        sql.appendChild(where);
        return sql;
    }
}
