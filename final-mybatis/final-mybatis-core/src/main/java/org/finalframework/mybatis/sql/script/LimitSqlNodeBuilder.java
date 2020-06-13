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


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * <pre>
 *     <code>
 *         <if test="query.limit != null>
 *              <trim prefix="LIMIT">
 *                  <if test="query.limit.offset != null">
 *                      #{query.limit.offset},
 *                  </if>
 *                  <if test="query.limit.limit != null">
 *                      #{query.limit.limit}
 *                  </if>
 *              </trim>
 *         </if>
 *     </code>
 * </pre>
 *
 * @author likly
 * @version 1.0
 * @date 2020-06-05 21:30:55
 * @since 1.0
 */
public class LimitSqlNodeBuilder implements ScriptSqlNodeBuilder {
    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();
        final Element ifNotNull = document.createElement("if");

        ifNotNull.setAttribute("test", String.format("%s != null", value));

        final Element trim = document.createElement("trim");
        trim.setAttribute("prefix", "LIMIT");

        final Element ifOffsetNotNull = document.createElement("if");
        ifOffsetNotNull.setAttribute("test", String.format("%s.offset != null", value));
        ifOffsetNotNull.appendChild(document.createTextNode(String.format("#{%s.offset},", value)));
        trim.appendChild(ifOffsetNotNull);


        final Element ifLimitNotNull = document.createElement("if");
        ifLimitNotNull.setAttribute("test", String.format("%s.limit != null", value));
        ifLimitNotNull.appendChild(document.createTextNode(String.format("#{%s.limit}", value)));
        trim.appendChild(ifLimitNotNull);


        ifNotNull.appendChild(trim);


        script.appendChild(ifNotNull);
    }
}

