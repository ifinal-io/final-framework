package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.LogicOperation;
import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.function.FunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 12:55:59
 * @since 1.0
 */
public class AndFunctionOperation implements FunctionOperation {
    private final Object value;

    public AndFunctionOperation(Object value) {
        this.value = value;
    }

    @Override
    public Operation operation() {
        return LogicOperation.AND;
    }

    @Override
    public String apply(Object value) {
        return String.format("%s & %s", value, this.value);
    }
}

