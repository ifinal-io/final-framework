package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.data.query.enums.Direction;
import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:15
 * @since 1.0
 */
@Getter
public class Order {
    private final QProperty property;
    private final Direction direction;

    public Order(QProperty property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return property.getColumn() + " " + direction.name();
    }
}
