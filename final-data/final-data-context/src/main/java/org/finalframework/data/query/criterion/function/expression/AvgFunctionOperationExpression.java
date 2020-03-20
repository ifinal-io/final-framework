package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@SupportFunctions(FunctionOperator.AVG)
public class AvgFunctionOperationExpression implements FunctionOperationExpression<FunctionOperation> {

    @Override
    public String expression(String target, FunctionOperation criterion) {
        return String.format("AVG(%s)", target);
    }
}
