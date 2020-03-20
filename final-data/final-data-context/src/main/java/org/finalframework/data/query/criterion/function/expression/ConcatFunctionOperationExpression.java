package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@SupportFunctions(value = FunctionOperator.CONCAT)
public class ConcatFunctionOperationExpression<T> implements FunctionOperationExpression<DoubleFunctionOperation<T>> {

    @Override
    public String expression(String target, DoubleFunctionOperation<T> criterion) {
        return String.format("CONCAT('%s',%s,'%s')",
                criterion.getFirstValue() == null ? "" : criterion.getFirstValue().toString(),
                target,
                criterion.getSecondValue() == null ? "" : criterion.getSecondValue().toString()
        );
    }
}
