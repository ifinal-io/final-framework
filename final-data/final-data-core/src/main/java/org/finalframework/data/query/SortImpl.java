package org.finalframework.data.query;

import org.finalframework.core.Assert;
import org.finalframework.data.query.builder.SortSqlBuilder;
import org.finalframework.data.query.enums.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 15:22:08
 * @since 1.0
 */
public class SortImpl implements Sort, Sql<Sort> {

    private final List<Order> orders;

    private SortImpl(Collection<Order> orders) {
        this.orders = new ArrayList<>(orders);
    }

    public static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    public static Sort by(Collection<Order> orders) {
        return new SortImpl(orders);
    }

    public static Sort asc(QProperty... property) {
        return sort(Direction.ASC, property);
    }

    public static Sort desc(QProperty... property) {
        return sort(Direction.DESC, property);
    }

    static Sort sort(Direction direction, QProperty... properties) {
        Assert.isEmpty(properties, "properties must be not empty!");
        return new SortImpl(Arrays.stream(properties).map(it -> Order.order(it, direction)).collect(Collectors.toList()));
    }

    public Sort and(Sort sort) {
        Assert.isNull(sort, "Sort must not be null!");
        ArrayList<Order> these = new ArrayList<>(this.orders);

        for (Order order : sort) {
            these.add(order);
        }

        return SortImpl.by(these);
    }


    @Override
    public Stream<Order> stream() {
        return orders.stream();
    }

    @Override
    public Iterator<Order> iterator() {
        return orders.iterator();
    }

    @Override
    public String getSql() {
        return new SortSqlBuilder(this).build();
    }
}
