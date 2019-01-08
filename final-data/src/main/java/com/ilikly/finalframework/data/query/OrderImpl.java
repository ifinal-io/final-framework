package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.query.enums.Direction;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 15:21:13
 * @since 1.0
 */
public class OrderImpl implements Order {
    private final QProperty property;
    private final Direction direction;

    OrderImpl(QProperty property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public QProperty getProperty() {
        return property;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return property.getColumn() + " " + direction.name();
    }

}
