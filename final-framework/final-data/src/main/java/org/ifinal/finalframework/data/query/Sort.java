package org.ifinal.finalframework.data.query;

import java.util.Arrays;
import java.util.Collection;
import org.ifinal.finalframework.annotation.query.Direction;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Sort extends Iterable<Order>, SqlNode {

    static Sort sort(final Direction direction, final QProperty<?>... properties) {

        return SortImpl.sort(direction, properties);
    }

    static Sort by(final Order... orders) {

        return by(Arrays.asList(orders));
    }

    static Sort by(final Collection<Order> orders) {

        return SortImpl.by(orders);
    }

    static Sort asc(final QProperty<?>... property) {

        return SortImpl.asc(property);
    }

    static Sort desc(final QProperty<?>... property) {

        return SortImpl.desc(property);
    }

    Sort and(final Sort sort);

    @Override
    default void apply(final StringBuilder sql, final String value) {

        sql.append("<trim prefix=\"ORDER BY\" suffixOverrides=\",\">");

        int index = 0;
        for (Order order : this) {
            order.apply(sql, String.format("value[%d]", index));
        }

        sql.append("</trim>");

    }

}
