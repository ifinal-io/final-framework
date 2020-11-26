package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.Direction;
import org.ifinal.finalframework.util.stream.Streamable;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Sort extends Streamable<Order>, Iterable<Order>, SqlNode {

    static Sort sort(Direction direction, QProperty<?>... properties) {
        return SortImpl.sort(direction, properties);
    }

    static Sort by(Order... orders) {
        return by(Arrays.asList(orders));
    }

    static Sort by(Collection<Order> orders) {
        return SortImpl.by(orders);
    }

    static Sort asc(QProperty<?>... property) {
        return SortImpl.asc(property);
    }

    static Sort desc(QProperty<?>... property) {
        return SortImpl.desc(property);
    }

    Sort and(Sort sort);

    @Override
    default void apply(StringBuilder sql, String value) {

        sql.append("<trim prefix=\"ORDER BY\" suffixOverrides=\",\">");

        int index = 0;
        for (Order order : this) {
            order.apply(sql, String.format("value[%d]", index));
        }

        sql.append("</trim>");

    }
}
