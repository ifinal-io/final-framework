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

import java.util.Optional;

/**
 * <pre>
 *     <code>
 *          <sql id="sql-table">
 *              <choose>
 *                  <when test="tableName != null">${tableName}</when>
 *              <otherwise>{Entity.simpleName}</otherwise>
 *              </choose>
 *          </sql>
 *     </code>
 * </pre>
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/table">table</a>
 **/
public final class SqlTablesFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlTablesFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_TABLES;
    }


    /**
     * <pre>
     *     <sql id="id">
     *         <choose>
     *             <when test="tableName != null">
     *                 ${tableName}
     *             </when>
     *             <otherwise>
     *                 tableName
     *             </otherwise>
     *         </choose>
     *     </sql>
     * </pre>
     *
     * @param document
     * @param entity
     * @return
     */
    @Override
    public Element buildSqlFragment(Document document, Entity entity) {
        //  <sql id="id">
        final Element sql = sql(document, id());
        //      <choose>
        final Element choose = document.createElement("choose");
        //              <when test="tableName != null">
        final Element whenTableNameNotNull = document.createElement("when");

        final String schema = Optional.ofNullable(entity.getSchema()).map(value -> value + ".").orElse("");

        whenTableNameNotNull.setAttribute("test", "table != null");
        //                  ${tableName}
        whenTableNameNotNull.appendChild(textNode(document, schema + "${table}"));
        //              </when>
        choose.appendChild(whenTableNameNotNull);
        //              <otherwise>
        final Element otherwise = document.createElement("otherwise");
        //                  tableName
        otherwise.appendChild(textNode(document, schema + entity.getTable()));
        //              </otherwise>
        choose.appendChild(otherwise);
        //      </choose>
        sql.appendChild(choose);
        return sql;
    }
}
