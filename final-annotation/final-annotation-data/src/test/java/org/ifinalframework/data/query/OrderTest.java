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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.ifinalframework.data.query.Direction;
import org.ifinalframework.data.query.Order;
import org.ifinalframework.data.query.QProperty;

/**
 * OrderTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
class OrderTest {

    @ParameterizedTest
    @ValueSource(strings = {"name", "name asc"})
    void from(String order) {
        Order name_asc = Order.from(order);
        assertEquals(Direction.ASC, name_asc.getDirection());
        assertEquals("name", name_asc.getColumn());
    }

    @Test
    void orderDescFrom() {
        Order order = Order.from("name desc");
        assertEquals(Direction.DESC, order.getDirection());
        assertEquals("name", order.getColumn());
    }

    @Test
    void fromException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Order.from("name asc test"));
    }

    @Test
    void testToString() {

        assertEquals("name ASC", Order.asc("name").toString());
        assertEquals("name DESC", Order.desc("name").toString());
    }

    @Test
    void testEquals() {
        QProperty<?> property = Mockito.mock(QProperty.class);
        Mockito.when(property.getColumn()).thenReturn("name");
        assertEquals(Order.asc("name"), Order.asc(property));
        assertEquals(Order.desc("name"), Order.desc(property));

    }

}
