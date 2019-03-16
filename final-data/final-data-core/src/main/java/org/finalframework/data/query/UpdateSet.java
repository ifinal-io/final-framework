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
public class UpdateSet {
    private final QProperty property;
    private final UpdateOperation operation;
    private final Object value;

    public UpdateSet(@NonNull QProperty property, @NonNull UpdateOperation operation, @NonNull Object value) {
        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public boolean isNull() {
        return null == value;
    }

    public QProperty getProperty() {
        return property;
    }

    public UpdateOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
