package org.finalframework.data.query.criterion.function.expression;


import org.finalframework.data.query.criterion.function.SupportTypes;
import org.finalframework.data.query.criterion.function.operation.FunctionOperation;
import org.finalframework.data.query.criterion.function.operation.FunctionOperationExpression;
import org.finalframework.data.query.operation.DateOperation;
import org.finalframework.data.query.operation.Operation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@SupportTypes(types = {Date.class, LocalDateTime.class, LocalDate.class, Object.class}
)
public class DateFunctionOperationExpression implements FunctionOperationExpression<FunctionOperation> {

    @Override
    public Operation operation() {
        return DateOperation.DATE;
    }

    @Override
    public String expression(String target, FunctionOperation criterion) {
        return String.format("DATE(%s)", target);
    }
}
