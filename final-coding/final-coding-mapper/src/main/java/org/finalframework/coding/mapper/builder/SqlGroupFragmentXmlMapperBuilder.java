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
 * @date 2019-11-22 15:54:15
 * @since 1.0
 */
public class SqlGroupFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {
    public SqlGroupFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    /**
     * <sql id="sql-group">
     * <if test="group != null">
     * <trim prefix="GROUP BY">
     * <foreach collection="group" item="property" separator=",">#{property.column}</foreach>
     * </trim>
     * </if>
     * </sql>
     */
    @Override
    protected Element buildSqlFragment(Document document, Entity entity) {
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        Element ifGroupNotNull = ifTest(document, "group != null");

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "GROUP BY");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "group");
        foreach.setAttribute("item", "property");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNode(document, "${property.column}"));

        trim.appendChild(foreach);

        ifGroupNotNull.appendChild(trim);

        sql.appendChild(ifGroupNotNull);

        return sql;
    }

    @Override
    public String id() {
        return SQL_GROUP;
    }
}

