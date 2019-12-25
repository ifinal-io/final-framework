package org.finalframework.data.query.function.operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:17:27
 * @since 1.0
 */
public class SingleFunctionOperation<T> extends SimpleFunctionOperation {
    private final T value;

    public SingleFunctionOperation(String operator, T value) {
        super(operator);
        this.value = value;
    }

    public T value() {
        return value;
    }
}
