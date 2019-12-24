package org.finalframework.data.query.criterion.function.operation;


import org.finalframework.data.query.criterion.FunctionCriterionOperation;
import org.finalframework.data.query.criterion.function.SimpleFunctionCriterion;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-24 14:01:37
 * @since 1.0
 */
@FunctionOperation(FunctionOperation.JSON_UNQUOTE)
public class JsonUnquoteFunctionCriterionOperation<T> implements FunctionCriterionOperation<SimpleFunctionCriterion> {

    @Override
    public String format(String column, SimpleFunctionCriterion criterion) {
        return String.format("JSON_UNQUOTE(%s)", column);
    }

}

