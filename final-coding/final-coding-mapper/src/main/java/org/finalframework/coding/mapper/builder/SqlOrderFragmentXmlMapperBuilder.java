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
 * @date 2019-10-11 18:33:19
 * @since 1.0
 */
public class SqlOrderFragmentXmlMapperBuilder extends AbsSqlFragmentXmlMapperBuilder {

    public SqlOrderFragmentXmlMapperBuilder(TypeHandlers typeHandlers) {
        super(typeHandlers);
    }

    @Override
    public String id() {
        return SQL_ORDER;
    }

    @Override
    public Element buildSqlFragment(Document document, Entity entity) {
        /**
         *     <sql id="sql-order">
         *         <if test="sort != null">
         *             <trim prefix="ORDER BY">
         *                 <foreach collection="sort" item="order" separator=",">
         *                     ${order.property.column} ${order.direction.name()}
         *                 </foreach>
         *             </trim>
         *         </if>
         *     </sql>
         */
        final Element sql = document.createElement("sql");
        sql.setAttribute("id", id());

        Element ifSortNotNull = ifTest(document, "sort != null");

        Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "ORDER BY");

        Element foreach = document.createElement("foreach");
        foreach.setAttribute("collection", "sort");
        foreach.setAttribute("item", "order");
        foreach.setAttribute("separator", ",");
        foreach.appendChild(textNode(document, "${order.property.column} ${order.direction.value}"));

        trim.appendChild(foreach);

        ifSortNotNull.appendChild(trim);

        sql.appendChild(ifSortNotNull);

        return sql;
    }
}
