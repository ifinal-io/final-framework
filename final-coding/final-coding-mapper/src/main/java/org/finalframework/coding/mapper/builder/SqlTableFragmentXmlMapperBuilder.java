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
 *              {Entity.simpleName}
 *          </sql>
 *     </code>
 * </pre>
 * <a href="https://final.ilikly.com/mybatis/mapper/fragment/table">table</a>
 **/
public final class SqlTableFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlTableFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_TABLE;
    }


    /**
     * <pre>
     *     <sql id="id">
     *      tableName
     *     </sql>
     * </pre>
     */
    @Override
    public Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = sql(document, id());
        final String schema = Optional.ofNullable(entity.getSchema()).map(value -> value + ".").orElse("");
        sql.appendChild(textNode(document, schema + entity.getTable()));
        return sql;
    }
}
