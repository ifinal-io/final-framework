/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.query;

import java.io.Serializable;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Sql order fragment builder.
 *
 * <pre class="code">
 *  Order.from("name desc");
 *  Order.asc("name");
 *  Order.desc("age");
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @see org.ifinalframework.core.Orderable
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
public final class Order implements Serializable {

    private final String column;

    private final Direction direction;

    public static Order from(String order) {

        String[] orderAndDirection = order.split(" ");

        if (orderAndDirection.length == 1) {
            return asc(order);
        } else if (orderAndDirection.length == 2) {
            return order(orderAndDirection[0], Direction.valueOf(orderAndDirection[1].toUpperCase()));
        }

        throw new IllegalArgumentException("Order need column and direction values bound found more in " + order);

    }

    /**
     * return order
     *
     * @param column    column
     * @param direction order direction
     * @return order
     */
    public static Order order(String column, Direction direction) {
        return new Order(column, Objects.requireNonNull(direction));
    }

    /**
     * @param property property
     * @return order
     */
    public static Order asc(QProperty<?> property) {
        return asc(property.getColumn());
    }

    /**
     * @param column column
     * @return order
     */
    public static Order asc(String column) {
        return order(column, Direction.ASC);
    }

    /**
     * @param property property
     * @return order
     */
    public static Order desc(QProperty<?> property) {
        return desc(property.getColumn());
    }

    /**
     *
     * @param column column
     * @return order
     */
    public static Order desc(String column) {
        return order(column, Direction.DESC);
    }

    @Override
    public String toString() {
        return String.join(" ", column, direction.name());
    }

}
