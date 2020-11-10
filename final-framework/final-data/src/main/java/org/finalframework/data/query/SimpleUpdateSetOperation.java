package org.finalframework.data.query;

import lombok.Getter;
import lombok.NonNull;
import org.finalframework.data.query.enums.UpdateOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:31
 * @since 1.0
 */
@Getter
public class SimpleUpdateSetOperation implements UpdateSetOperation {
    private final QProperty<?> property;
    private final UpdateOperation operation;
    private final Object value;

    public SimpleUpdateSetOperation(@NonNull QProperty<?> property, @NonNull UpdateOperation operation, @NonNull Object value) {
        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public boolean isNull() {
        return null == value;
    }

    public QProperty<?> getProperty() {
        return property;
    }

    public UpdateOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }


    @Override
    public void apply(StringBuilder sql, String expression) {

        sql.append("<trim>");
        sql.append(property.getColumn());


        switch (operation) {
            case EQUAL:
                // column = #{expression.value,javaType=?,typeHandler=?}
                sql.append(" = ").append("#{").append(expression).append(".value");

                sql.append(",javaType=").append(property.getType().getCanonicalName());

                if (property.getTypeHandler() != null) {
                    sql.append(",typeHandler=").append(property.getTypeHandler().getCanonicalName());
                }

                sql.append("},");

                break;

            case INC:
            case INCR:
                // column = column + #{expression.value}
                sql.append(" = ").append(property.getColumn()).append(" + #{").append(expression).append(".value},");
                break;

            case DEC:
            case DECR:
                // column = column - #{expression.value}
                sql.append(" = ").append(property.getColumn()).append(" - #{").append(expression).append(".value},");
                break;
        }

        sql.append("</trim>");

    }

}
