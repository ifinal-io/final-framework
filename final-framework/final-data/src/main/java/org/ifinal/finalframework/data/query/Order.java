package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.Direction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Order extends SqlNode {

    static Order order(QProperty<?> property, Direction direction) {
        return new OrderImpl(property, direction);
    }

    static Order asc(QProperty<?> property) {
        return order(property, Direction.ASC);
    }

    static Order desc(QProperty<?> property) {
        return order(property, Direction.DESC);
    }

    @SuppressWarnings("rawtypes")
    QProperty getProperty();

    Direction getDirection();

    @Override
    default void apply(@NonNull StringBuilder sql, @NonNull String value) {
        sql.append(String.format("%s %s,", getProperty().getColumn(), getDirection()));
    }

}
