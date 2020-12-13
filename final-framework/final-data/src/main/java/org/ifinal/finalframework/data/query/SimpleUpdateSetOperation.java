package org.ifinal.finalframework.data.query;

import java.util.Objects;
import lombok.Getter;
import org.ifinal.finalframework.data.query.enums.UpdateOperation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
class SimpleUpdateSetOperation implements UpdateSetOperation {

    private final QProperty<?> property;

    private final UpdateOperation operation;

    private final Object value;

    SimpleUpdateSetOperation(final QProperty<?> property, final UpdateOperation operation) {

        this(property, operation, null);
    }

    SimpleUpdateSetOperation(final @NonNull QProperty<?> property, final @NonNull UpdateOperation operation, final @Nullable Object value) {

        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public boolean isNull() {
        return Objects.isNull(value);
    }

    @Override
    public void apply(final @NonNull StringBuilder sql, final @NonNull String expression) {

        sql.append("<trim>");
        sql.append(property.getColumn());

        switch (operation) {
            case EQUAL:
                sql.append(" = ").append("#{").append(expression).append(".value");

                sql.append(",javaType=").append(property.getType().getCanonicalName());

                if (property.getTypeHandler() != null) {
                    sql.append(",typeHandler=").append(property.getTypeHandler().getCanonicalName());
                }

                sql.append("},");

                break;

            case INC:
            case INCR:
                sql.append(" = ").append(property.getColumn()).append(" + #{").append(expression).append(".value},");
                break;

            case DEC:
            case DECR:
                sql.append(" = ").append(property.getColumn()).append(" - #{").append(expression).append(".value},");
                break;
            default:
                throw new IllegalArgumentException(operation.name());
        }

        sql.append("</trim>");

    }

}
