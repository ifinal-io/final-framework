package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.DoubleFunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 21:11:49
 * @since 1.0
 */
@SupportTypes
public class JsonContainsFunctionOperationExpression<T> implements FunctionOperationExpression<DoubleFunctionOperation<T>> {

    @Override
    public Operation operation() {
        return JsonOperation.JSON_CONTAINS;
    }

    @Override
    public String expression(String target, DoubleFunctionOperation<T> criterion) {

        if (criterion.getFirstValue() instanceof String) {
            if (criterion.getSecondValue() == null) {
                return String.format("JSON_CONTAINS(%s,'\"%s\"')", target, criterion.getFirstValue().toString());
            } else {
                return String.format("JSON_CONTAINS(%s,'\"%s\"','%s')", target, criterion.getFirstValue().toString(), criterion.getSecondValue().toString());
            }
        }

        if (criterion.getSecondValue() == null) {
            return String.format("JSON_CONTAINS(%s,'%s')", target, criterion.getFirstValue().toString());
        } else {
            return String.format("JSON_CONTAINS(%s,'%s','%s')", target, criterion.getFirstValue().toString(), criterion.getSecondValue().toString());
        }


    }

}
