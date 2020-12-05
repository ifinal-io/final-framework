package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.Direction;
import org.ifinal.finalframework.util.Asserts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class SortImpl extends ArrayList<Order> implements Sort {

    private SortImpl(Collection<Order> orders) {
        this.addAll(orders);
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
        Asserts.isEmpty(properties, "properties must be not empty!");
        return new SortImpl(Arrays.stream(properties).map(it -> Order.order(it, direction)).collect(Collectors.toList()));
    }

    public Sort and(Sort sort) {
        Asserts.isNull(sort, "Sort must not be null!");
        ArrayList<Order> these = new ArrayList<>(this);

        for (Order order : sort) {
            these.add(order);
        }

        return SortImpl.by(these);
    }

    @Override
    public String toString() {
        return isEmpty() ? "" : stream().map(Order::toString).collect(Collectors.joining(","));
    }

}
