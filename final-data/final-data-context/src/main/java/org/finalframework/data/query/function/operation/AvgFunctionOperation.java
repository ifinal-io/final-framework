package org.finalframework.data.query.function.operation;


import org.finalframework.data.query.DefaultFunctionOperator;
import org.finalframework.data.query.FunctionCriterion;
import org.finalframework.data.query.FunctionOperation;
import org.finalframework.data.query.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
public class AvgFunctionOperation implements FunctionOperation<FunctionCriterion> {

    @Override
    public FunctionOperator operator() {
        return DefaultFunctionOperator.AVG;
    }

    @Override
    public String format(String column, FunctionCriterion criterion) {
        return String.format("AVG(%s)", column);
    }
}
