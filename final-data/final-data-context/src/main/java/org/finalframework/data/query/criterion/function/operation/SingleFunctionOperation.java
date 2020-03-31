package org.finalframework.data.query.criterion.function.operation;

import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:17:27
 * @since 1.0
 */
public class SingleFunctionOperation<T> extends SimpleFunctionOperation {
    private final T value;

    public SingleFunctionOperation(Operation operation, T value) {
        super(operation);
        this.value = value;
    }

    public T value() {
        return value;
    }
}
