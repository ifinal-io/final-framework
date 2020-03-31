package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-25 13:07:15
 * @since 1.0
 */
public class DoubleFunctionOperation<T> extends SimpleFunctionOperation {
    private final T firstValue;
    private final T secondValue;

    public DoubleFunctionOperation(Operation operation, T firstValue, T secondValue) {
        super(operation);
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public T getFirstValue() {
        return firstValue;
    }

    public T getSecondValue() {
        return secondValue;
    }
}

