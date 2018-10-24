package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.Direction;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import cn.com.likly.finalframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 22:06
 * @since 1.0
 */
public class Sort implements Iterable<Order>, Serializable {
    private List<Order> orders;

    private Sort(List<Order> orders) {
        this.orders = orders;
    }

    public static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    public static Sort by(List<Order> orders) {
        Assert.isEmpty(orders, "orders is null or empty");
        return new Sort(orders);
    }

    public static Sort asc(PropertyHolder... property) {
        return of(Direction.ASC, property);
    }

    public static Sort desc(PropertyHolder... property) {
        return of(Direction.DESC, property);
    }

    private static Sort of(Direction direction, PropertyHolder... properties) {
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
    public Iterator<Order> iterator() {
        return orders.iterator();
    }
}
