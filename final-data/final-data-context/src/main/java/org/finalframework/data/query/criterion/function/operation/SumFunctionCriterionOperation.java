package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.FunctionCriterionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionOperation(FunctionOperation.SUM)
public class SumFunctionCriterionOperation implements FunctionCriterionOperation<FunctionCriterion> {

    @Override
    public String format(String column, FunctionCriterion criterion) {
        return String.format("SUM(%s)", column);
    }
}
