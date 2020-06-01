package org.finalframework.data.query.operation.function;


import org.finalframework.data.query.operation.LogicOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 12:55:59
 * @since 1.0
 */
public class XorFunctionOperation implements FunctionOperation {
    private final Object value;

    public XorFunctionOperation(Object value) {
        this.value = value;
    }

    @Override
    public Operation operation() {
        return LogicOperation.XOR;
    }

    @Override
    public String apply(Object value) {
        return String.format("%s ^ %s", value, this.value);
    }
}

