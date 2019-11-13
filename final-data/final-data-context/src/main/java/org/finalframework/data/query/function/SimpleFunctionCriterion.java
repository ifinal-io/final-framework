package org.finalframework.data.query.function;


import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:18:52
 * @since 1.0
 */
public class SimpleFunctionCriterion implements FunctionCriterion {
    private final FunctionOperator operator;

    public SimpleFunctionCriterion(FunctionOperator operator) {
        this.operator = operator;
    }

    @Override
    public FunctionOperator operator() {
        return operator;
    }
}
