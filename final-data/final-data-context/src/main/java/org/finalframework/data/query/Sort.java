package org.finalframework.data.query;

import org.finalframework.core.Streamable;
import org.finalframework.data.query.enums.Direction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:20
 * @since 1.0
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
    default void apply(Node parent, String value) {
        final Document document = parent.getOwnerDocument();
        final Element sort = document.createElement("trim");
        sort.setAttribute("prefix", "ORDER BY");
        sort.setAttribute("suffixOverrides", ",");
        int index = 0;
        for (Order order : this) {
            order.apply(sort, String.format("value[%d]", index));
        }
        parent.appendChild(sort);
    }
}
