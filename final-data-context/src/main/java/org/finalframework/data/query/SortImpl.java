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

import org.finalframework.annotation.query.Direction;
import org.finalframework.core.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 15:22:08
 * @since 1.0
 */
public class SortImpl extends ArrayList<Order> implements Sort {

//    private final List<Order> orders;

    private SortImpl(Collection<Order> orders) {
        this.addAll(orders);
//        this.orders = new ArrayList<>(orders);
    }

    public static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    public static Sort by(Collection<Order> orders) {
        return new SortImpl(orders);
    }

    public static Sort asc(QProperty<?>... property) {
        return sort(Direction.ASC, property);
    }

    public static Sort desc(QProperty<?>... property) {
        return sort(Direction.DESC, property);
    }

    static Sort sort(Direction direction, QProperty<?>... properties) {
        Assert.isEmpty(properties, "properties must be not empty!");
        return new SortImpl(Arrays.stream(properties).map(it -> Order.order(it, direction)).collect(Collectors.toList()));
    }

    public Sort and(Sort sort) {
        Assert.isNull(sort, "Sort must not be null!");
        ArrayList<Order> these = new ArrayList<>(this);

        for (Order order : sort) {
            these.add(order);
        }

        return SortImpl.by(these);
    }

    @Override
    public Stream<Order> stream() {
        return super.stream();
    }

    @Override
    public String toString() {
        return isEmpty() ? "" : stream().map(Order::toString).collect(Collectors.joining(","));
    }

}
