package org.finalframework.data.query.function.expression;


import org.finalframework.data.query.function.operation.FunctionOperation;
import org.finalframework.data.query.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:21:33
 * @since 1.0
 */

@FunctionExpression(
        value = FunctionExpression.NOT,
        types = {
                byte.class, Byte.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class
        }
)
public class NumberNotFunctionOperationExpression<T extends Number> implements FunctionOperationExpression<FunctionOperation> {

    @Override
    public String expression(String target, FunctionOperation criterion) {
        return String.format("~ %s", target);
    }
}
