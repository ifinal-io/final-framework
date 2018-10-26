package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.Direction;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.Getter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:15
 * @since 1.0
 */
@Getter
public class Order {
    private final Property property;
    private final Direction direction;

    public Order(Property property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return property.getColumn() + " " + direction.name();
    }
}
