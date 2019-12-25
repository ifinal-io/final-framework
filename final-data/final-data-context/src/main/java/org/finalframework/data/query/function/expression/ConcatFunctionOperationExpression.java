package org.finalframework.data.query.function.expression;


import org.finalframework.data.query.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionExpression(value = FunctionExpression.CONCAT)
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
