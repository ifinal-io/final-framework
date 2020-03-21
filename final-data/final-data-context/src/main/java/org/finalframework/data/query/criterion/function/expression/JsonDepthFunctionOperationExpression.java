package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SimpleFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-21 14:01:37
 * @since 1.0
 */
@SupportFunctions(FunctionOperator.JSON_DEPTH)
public class JsonDepthFunctionOperationExpression<T> implements FunctionOperationExpression<SimpleFunctionOperation> {

    @Override
    public String expression(String target, SimpleFunctionOperation criterion) {
        return String.format("JSON_DEPTH(%s)", target);
    }

}

