package org.finalframework.data.query.function.expression;


import org.finalframework.data.query.function.annotation.FunctionOperator;
import org.finalframework.data.query.function.operation.FunctionOperation;
import org.finalframework.data.query.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionOperator(FunctionOperator.SUM)
public class SumFunctionOperationExpression implements FunctionOperationExpression<FunctionOperation> {

    @Override
    public String expression(String target, FunctionOperation criterion) {
        return String.format("SUM(%s)", target);
    }
}
