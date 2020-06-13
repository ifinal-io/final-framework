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
    public SqlWhereCriteriaFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_WHERE_CRITERIA;
    }

    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        Element sql = sql(document, id());
        Element where = document.createElement("where");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "criteria");
        foreach.setAttribute("item", "criterion");
        foreach.setAttribute("separator", " AND ");
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
