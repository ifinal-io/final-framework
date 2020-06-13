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
 * @author likly
 * @version 1.0
 * @date 2020-06-05 22:38:54
 * @since 1.0
 */
public class CriteriaSqlNodeBuilder implements ScriptSqlNodeBuilder {
    private final Integer maxLevel;

    public CriteriaSqlNodeBuilder(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public void build(Node script, String value) {
        final Document document = script.getOwnerDocument();

        final Element ifCriteriaNotNull = document.createElement("if");
        ifCriteriaNotNull.setAttribute("test", String.format("%s != null", value));


        script.appendChild(ifCriteriaNotNull);
    }


}

