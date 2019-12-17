package org.finalframework.data.query.criterion.function;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:17:27
 * @since 1.0
 */
public class SingleFunctionCriterion<T> extends SimpleFunctionCriterion {
    private final T value;

    public SingleFunctionCriterion(String operator, T value) {
        super(operator);
        this.value = value;
    }

    public T value() {
        return value;
    }
}
