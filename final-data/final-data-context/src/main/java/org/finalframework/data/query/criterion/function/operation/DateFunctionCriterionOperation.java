package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionCriterionOperation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionOperation(
        value = FunctionOperation.DATE,
        types = {Date.class, LocalDateTime.class, LocalDate.class}
)
public class DateFunctionCriterionOperation implements FunctionCriterionOperation<FunctionCriterion> {

    @Override
    public String format(String column, FunctionCriterion criterion) {
        return String.format("DATE(%s)", column);
    }
}
