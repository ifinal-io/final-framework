package org.finalframework.data.query;

import org.finalframework.data.query.enums.Direction;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-08 15:21:13
 * @since 1.0
 */
public class OrderImpl implements Order {
    private final String table;
    private final QProperty property;
    private final Direction direction;

    OrderImpl(String table, QProperty property, Direction direction) {
        this.table = table;
        this.property = property;
        this.direction = direction;
    }

    @Override
    public String table() {
        return table;
    }

    @Override
    public QProperty property() {
        return property;
    }

    @Override
    public Direction direction() {
        return direction;
    }

    @Override
    public String toString() {
        return this.table.equals(property.getTable())
                ? property.getColumn() + " " + direction.name()
                : String.format("%s.%s %s",table,property.getColumn(),direction.name());

    }

}
