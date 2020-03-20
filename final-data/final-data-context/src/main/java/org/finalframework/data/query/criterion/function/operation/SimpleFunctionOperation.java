package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.function.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:18:52
 * @since 1.0
 */
public class SimpleFunctionOperation implements FunctionOperation {
    private final FunctionOperator operator;

    public SimpleFunctionOperation(FunctionOperator operator) {
        this.operator = operator;
    }

    @Override
    public FunctionOperator operator() {
        return operator;
    }
}
