package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.Direction;
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
public class Sort<T> implements Iterable<Order<T>>, Serializable {
    private List<Order<T>> orders;

    private Sort(List<Order<T>> orders) {
        this.orders = orders;
    }

    public static <T> Sort<T> by(Order<T>... orders) {
        return by(Arrays.asList(orders));
    }

    public static <T> Sort<T> by(List<Order<T>> orders) {
        Assert.isEmpty(orders, "orders is null or empty");
        return new Sort<T>(orders);
    }

    public static <T> Sort<T> asc(T... property) {
        return of(Direction.ASC, property);
    }

    public static <T> Sort<T> desc(T... property) {
        return of(Direction.DESC, property);
    }

    private static <T> Sort<T> of(Direction direction, T... properties) {
        Assert.isEmpty(properties, "properties must be not empty!");
        return new Sort<T>(Arrays.stream(properties).map(it -> new Order<>(it, direction)).collect(Collectors.toList()));
    }

    public Sort<T> and(Sort<T> sort) {
        Assert.isNull(sort, "Sort<T> must not be null!");
        ArrayList<Order<T>> these = new ArrayList<>(this.orders);

        for (Order<T> order : sort) {
            these.add(order);
        }

        return Sort.by(these);
    }

    @Override
    public Iterator<Order<T>> iterator() {
        return orders.iterator();
    }
}
