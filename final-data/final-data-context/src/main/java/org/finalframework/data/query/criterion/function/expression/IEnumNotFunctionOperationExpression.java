package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 16:06:50
 * @since 1.0
 */
@SupportFunctions(
        value = FunctionOperator.NOT,
        types = {IEnum.class}
)
public class IEnumNotFunctionOperationExpression<T extends IEnum> implements FunctionOperationExpression<SingleFunctionOperation<T>> {
    @Override
    public String expression(String target, SingleFunctionOperation<T> criterion) {
        return String.format("%s ^ %s", target, String.valueOf(criterion.value().getCode()));
    }
}

