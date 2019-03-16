package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.query.enums.Direction;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:20
 * @since 1.0
 */
public interface Sort extends Streamable<Order>, Iterable<Order> {

    static Sort sort(Direction direction, QProperty... properties) {
        return SortImpl.sort(direction, properties);
    }

    static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    static Sort by(Collection<Order> orders) {
        return SortImpl.by(orders);
    }

    static Sort asc(QProperty... property) {
        return SortImpl.asc(property);
    }

    static Sort desc(QProperty... property) {
        return SortImpl.desc(property);
    }

    Sort and(Sort sort);

}
