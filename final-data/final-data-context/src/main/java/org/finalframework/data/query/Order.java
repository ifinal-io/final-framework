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

package org.finalframework.data.query;

import org.finalframework.data.query.enums.Direction;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:15
 * @since 1.0
 */
public interface Order extends SqlNode {

    static Order order(QProperty property, Direction direction) {
        return new OrderImpl(property, direction);
    }

    static Order asc(QProperty property) {
        return order(property, Direction.ASC);
    }

    static Order desc(QProperty property) {
        return order(property, Direction.DESC);
    }

    QProperty getProperty();

    Direction getDirection();

    @Override
    default void apply(Node parent, String value) {
        final Document document = parent.getOwnerDocument();
        parent.appendChild(document.createTextNode(String.format("%s %s,", getProperty().getColumn(), getDirection())));
    }
}
