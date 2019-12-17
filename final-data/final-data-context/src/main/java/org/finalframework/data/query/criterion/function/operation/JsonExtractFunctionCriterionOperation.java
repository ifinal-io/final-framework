package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterionOperation;
import org.finalframework.data.query.criterion.function.SingleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 21:11:49
 * @since 1.0
 */
@FunctionOperation(FunctionOperation.JSON_EXTRACT)
public class JsonExtractFunctionCriterionOperation<T> implements FunctionCriterionOperation<SingleFunctionCriterion<T>> {

    @Override
    public String format(String column, SingleFunctionCriterion<T> criterion) {
        return String.format("JSON_EXTRACT(%s,'%s')", column, criterion.value());
    }

}
