package cn.com.likly.finalframework.data.query;

import cn.com.likly.finalframework.core.Assert;
import cn.com.likly.finalframework.core.Streable;
import cn.com.likly.finalframework.data.query.enums.Direction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:20
 * @since 1.0
 */
public class Sort implements Streable<Order>, Iterable<Order> {

    private final Set<QProperty> propertiesToSort;
    private final List<Order> orders;

    private Sort(Collection<Order> orders) {
        this.orders = new ArrayList<>(orders);
        this.propertiesToSort = orders.stream().map(Order::getProperty).collect(Collectors.toSet());
    }

    public static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    public static Sort by(Collection<Order> orders) {
        return new Sort(orders);
    }

    public static Sort asc(QProperty... property) {
        return of(Direction.ASC, property);
    }

    public static Sort desc(QProperty... property) {
        return of(Direction.DESC, property);
    }

    private static Sort of(Direction direction, QProperty... properties) {
        Assert.isEmpty(properties, "properties must be not empty!");
        return new Sort(Arrays.stream(properties).map(it -> new Order(it, direction)).collect(Collectors.toList()));
    }

    public Sort and(Sort sort) {
        Assert.isNull(sort, "Sort must not be null!");
        ArrayList<Order> these = new ArrayList<>(this.orders);

        for (Order order : sort) {
            these.add(order);
        }

        return Sort.by(these);
    }


    @Override
    public Stream<Order> stream() {
        return orders.stream();
    }

    @Override
    public Iterator<Order> iterator() {
        return orders.iterator();
    }
}
