package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.operator.DefaultFunctionOperator;
import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionOperation;
import org.finalframework.data.query.criterion.operator.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
public class SumFunctionOperation implements FunctionOperation<FunctionCriterion> {

    @Override
    public FunctionOperator operator() {
        return DefaultFunctionOperator.SUM;
    }

    @Override
    public String format(String column, FunctionCriterion criterion) {
        return String.format("SUM(%s)", column);
    }
}
