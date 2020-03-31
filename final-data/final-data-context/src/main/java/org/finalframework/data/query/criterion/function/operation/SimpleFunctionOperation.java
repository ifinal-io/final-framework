package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:18:52
 * @since 1.0
 */
public class SimpleFunctionOperation implements FunctionOperation {
    private final Operation operation;

    public SimpleFunctionOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public Operation operation() {
        return operation;
    }
}
