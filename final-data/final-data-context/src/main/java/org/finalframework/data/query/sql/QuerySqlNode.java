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

package org.finalframework.data.query.sql;


import org.finalframework.data.mapping.Entity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 18:05:29
 * @since 1.0
 */
public class QuerySqlNode {
    public void apply(Node parent, String expression, Object query) {
        final Document document = parent.getOwnerDocument();

        final Entity<?> entity = Entity.from(query.getClass());


    }
}

