package org.finalframework.data.query.function.operation;


/**
 * @author likly
 * @version 1.0
 * @date 2019-12-25 13:07:15
 * @since 1.0
 */
public class DoubleFunctionOperation<T> extends SimpleFunctionOperation {
    private final T firstValue;
    private final T secondValue;

    public DoubleFunctionOperation(String operator, T firstValue, T secondValue) {
        super(operator);
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

