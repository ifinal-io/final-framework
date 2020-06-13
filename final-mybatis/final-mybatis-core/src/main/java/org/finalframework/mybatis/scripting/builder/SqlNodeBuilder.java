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

package org.finalframework.mybatis.scripting.builder;


import org.finalframework.core.Builder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-05 17:14:49
 * @since 1.0
 */
public abstract class SqlNodeBuilder implements Builder<Element> {

    private final Document document;
    private final Element node;

    public SqlNodeBuilder(Document document, String name) {
        this.document = document;
        this.node = document.createElement(name);
    }

    protected void setAttribute(String name, String value) {
        this.node.setAttribute(name, value);
    }

    public SqlNodeBuilder appendChild(Node... children) {
        for (Node child : children) {
            this.node.appendChild(child);
        }
        return this;
    }


    @Override
    public Element build() {
        return node;
    }
}

