package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.FunctionOperator;
import org.finalframework.data.query.criterion.function.SupportFunctions;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 21:11:49
 * @since 1.0
 */
@SupportFunctions(FunctionOperator.JSON_CONTAINS)
public class JsonContainsFunctionOperationExpression<T> implements FunctionOperationExpression<DoubleFunctionOperation<T>> {

    @Override
    public String expression(String target, DoubleFunctionOperation<T> criterion) {
        if (criterion.getSecondValue() == null) {
            return String.format("JSON_CONTAINS(%s,'%s')", target, criterion.getFirstValue().toString());
        } else {
            return String.format("JSON_CONTAINS(%s,'%s','%s')", target, criterion.getFirstValue().toString(), criterion.getSecondValue().toString());
        }
    }

}
