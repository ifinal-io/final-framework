package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@SupportTypes
public class JsonExtractFunctionOperationExpression<T> implements FunctionOperationExpression<SingleFunctionOperation<T>> {

    @Override
    public Operation operation() {
        return JsonOperation.JSON_EXTRACT;
    }

    @Override
    public String expression(String target, SingleFunctionOperation<T> criterion) {
        return String.format("JSON_EXTRACT(%s,'%s')", target, criterion.value());
    }

}
