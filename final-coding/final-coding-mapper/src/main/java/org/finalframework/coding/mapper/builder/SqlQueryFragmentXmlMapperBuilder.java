/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.coding.mapper.builder;

import org.finalframework.coding.entity.Entity;
import org.finalframework.coding.entity.Property;
import org.finalframework.coding.mapper.TypeHandlers;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/query">query</a>
 * <pre>
 *     <code>
 *          <sql id="sql-query">
 *              <if test="query != null">
 *                  <bind name="criteria" value="query.criteria"/>
 *                  <bind name="sort" value="query.sort"/>
 *                  <bind name="limit" value="query.limit"/>
 *                  <include refid="sql-criteria"/>
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
 * @see SqlWhereCriteriaFragmentXmlMapperBuilder
 * @see SqlGroupFragmentXmlMapperBuilder
 * @see SqlOrderFragmentXmlMapperBuilder
 * @see SqlLimitFragmentXmlMapperBuilder
 * @since 1.0
 */
public class SqlQueryFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlQueryFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_QUERY;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {

        final Element sql = sql(document, id());
        Element ifQueryNotNull = ifTest(document, "query != null");

        ifQueryNotNull.appendChild(bind(document, "criteria", "query.criteria"));
        ifQueryNotNull.appendChild(bind(document, "group", "query.group"));
        ifQueryNotNull.appendChild(bind(document, "sort", "query.sort"));
        ifQueryNotNull.appendChild(bind(document, "limit", "query.limit"));
        ifQueryNotNull.appendChild(include(document, SQL_WHERE_CRITERIA));
        ifQueryNotNull.appendChild(include(document, SQL_GROUP));
        ifQueryNotNull.appendChild(include(document, SQL_ORDER));
        ifQueryNotNull.appendChild(include(document, SQL_LIMIT));
        sql.appendChild(ifQueryNotNull);
        return sql;
    }

}
