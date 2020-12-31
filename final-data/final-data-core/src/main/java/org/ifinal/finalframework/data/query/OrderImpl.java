package org.ifinal.finalframework.data.query;

import lombok.Getter;
import org.ifinal.finalframework.annotation.query.Direction;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
final class OrderImpl implements Order {

    private final QProperty<?> property;

    private final Direction direction;

    OrderImpl(final QProperty<?> property, final Direction direction) {

        this.property = property;
        this.direction = direction;
    }

}
