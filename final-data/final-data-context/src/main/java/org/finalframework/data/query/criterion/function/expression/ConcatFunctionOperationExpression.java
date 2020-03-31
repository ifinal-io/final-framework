package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.operation.Operation;
import org.finalframework.data.query.operation.StringOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@SupportTypes
public class ConcatFunctionOperationExpression<T> implements FunctionOperationExpression<DoubleFunctionOperation<T>> {

    @Override
    public Operation operation() {
        return StringOperation.CONCAT;
    }

    @Override
    public String expression(String target, DoubleFunctionOperation<T> criterion) {
        return String.format("CONCAT('%s',%s,'%s')",
                criterion.getFirstValue() == null ? "" : criterion.getFirstValue().toString(),
                target,
                criterion.getSecondValue() == null ? "" : criterion.getSecondValue().toString()
        );
    }
}
