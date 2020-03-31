package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.annotation.IEnum;
import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.criterion.function.operation.SingleFunctionOperation;
import org.finalframework.data.query.operation.LogicOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 16:06:50
 * @since 1.0
 */
@SupportTypes(types = {IEnum.class}
)
public class IEnumAndFunctionOperationExpression<T extends IEnum> implements FunctionOperationExpression<SingleFunctionOperation<T>> {
    @Override
    public Operation operation() {
        return LogicOperation.AND;
    }

    @Override
    public String expression(String target, SingleFunctionOperation<T> criterion) {
        return String.format("%s & %s", target, String.valueOf(criterion.value().getCode()));
    }
}

