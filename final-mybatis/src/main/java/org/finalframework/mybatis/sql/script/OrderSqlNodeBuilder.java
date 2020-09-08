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

package org.finalframework.mybatis.sql.script;


import org.finalframework.annotation.query.Direction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 *     <code>
 *         <if test="query.sort != null>
 *              <foreach collection="query.sort" item="order" open="ORDER BY" separator=",">
 *                  ${order.property.column} ${order.direction.name()}
 *              </foreach>
 *         </if>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-05 21:58:29
 * @see org.finalframework.data.query.Sort
 * @see org.finalframework.data.query.Order
 * @see Direction
 * @since 1.0
 */
public class OrderSqlNodeBuilder implements ScriptSqlNodeBuilder {
    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();

        final Element ifSortNotNull = document.createElement("if");
        ifSortNotNull.setAttribute("test", String.format("%s != null", value));

        final Element foreach = document.createElement("foreach");

        foreach.setAttribute("collection", value);
        foreach.setAttribute("item", "order");
        foreach.setAttribute("open", "ORDER BY");
        foreach.setAttribute("separator", ",");

        foreach.appendChild(document.createCDATASection("${order.property.column} ${order.direction.name()}"));

        ifSortNotNull.appendChild(foreach);

        script.appendChild(ifSortNotNull);
    }
}

