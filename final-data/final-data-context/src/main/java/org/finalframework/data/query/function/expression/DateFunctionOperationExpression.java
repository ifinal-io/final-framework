package org.finalframework.data.query.function.expression;


import org.finalframework.data.query.function.annotation.FunctionOperator;
import org.finalframework.data.query.function.operation.FunctionOperation;
import org.finalframework.data.query.function.operation.FunctionOperationExpression;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionOperator(
        value = FunctionOperator.DATE,
        types = {Date.class, LocalDateTime.class, LocalDate.class, Object.class}
)
public class DateFunctionOperationExpression implements FunctionOperationExpression<FunctionOperation> {

    @Override
    public String expression(String target, FunctionOperation criterion) {
        return String.format("DATE(%s)", target);
    }
}
