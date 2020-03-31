package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.finalframework.data.query.operation.LogicOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @since 1.0
 */

@SupportTypes(types = {
        byte.class, Byte.class,
        short.class, Short.class,
        int.class, Integer.class,
        long.class, Long.class,
        float.class, Float.class,
        double.class, Double.class
}
)
public class NumberOrFunctionOperationExpression<T extends Number> implements FunctionOperationExpression<SingleFunctionOperation<T>> {

    @Override
    public Operation operation() {
        return LogicOperation.OR;
    }

    @Override
    public String expression(String target, SingleFunctionOperation<T> criterion) {
        return String.format("%s | %s", target, criterion.value());
    }
}
