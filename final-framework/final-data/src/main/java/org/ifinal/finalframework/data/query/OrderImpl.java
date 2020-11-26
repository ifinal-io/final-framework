package org.ifinal.finalframework.data.query;

import org.ifinal.finalframework.annotation.query.Direction;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class OrderImpl implements Order {
    private final QProperty<?> property;
    private final Direction direction;

    OrderImpl(QProperty<?> property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }


    @Override
    public QProperty<?> getProperty() {
        return property;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }


}
