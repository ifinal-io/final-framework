package org.finalframework.data.query.function.operation;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:18:52
 * @since 1.0
 */
public class SimpleFunctionOperation implements FunctionOperation {
    private final String operator;

    public SimpleFunctionOperation(String operator) {
        this.operator = operator;
    }

    @Override
    public String operator() {
        return operator;
    }
}
