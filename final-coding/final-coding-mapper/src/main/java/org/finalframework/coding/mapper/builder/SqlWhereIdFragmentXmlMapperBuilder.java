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
 * @author likly
 * @version 1.0
 * @date 2019-10-13 12:15:40
 * @since 1.0
 */
public class SqlWhereIdFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlWhereIdFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

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
    public Element buildSqlFragment(Document document, Entity entity) {
        Element sql = sql(document, id());
        Element where = document.createElement("where");
        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", String.format("%s =", typeHandlers.formatPropertyColumn(entity, null, entity.getRequiredIdProperty())));
        trim.appendChild(textNode(document, "#{id}"));
        where.appendChild(trim);
        sql.appendChild(where);
        return sql;
    }
}
