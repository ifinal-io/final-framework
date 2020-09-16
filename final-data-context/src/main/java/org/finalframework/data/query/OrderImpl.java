package org.finalframework.data.query;

import org.finalframework.annotation.query.Direction;

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


}
