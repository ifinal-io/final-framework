package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @see NumberOrFunctionOperationExpression
 * @see NumberXorFunctionOperationExpression
 * @see NumberNotFunctionOperationExpression
 * @since 1.0
 */
@SupportFunctions(
        value = FunctionOperator.AND,
        types = {
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class
        }
)
public class NumberAndFunctionOperationExpression<T> implements FunctionOperationExpression<SingleFunctionOperation<T>> {

    @Override
    public String expression(String target, SingleFunctionOperation<T> criterion) {
        return String.format("%s & %s", target, criterion.value());
    }
}
