package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.SetOperation;
import cn.com.likly.finalframework.data.mapping.Property;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:31
 * @since 1.0
 */
@Getter
public class UpdateSet {
    private final Property property;
    private final SetOperation operation;
    private final Object value;

    public UpdateSet(@NonNull Property property, @NonNull SetOperation operation, @NonNull Object value) {
        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public boolean isNull() {
        return null == value;
    }

    public Property getProperty() {
        return property;
    }

    public SetOperation getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
