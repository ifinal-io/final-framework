package org.finalframework.data.query.criterion.function;

import org.finalframework.data.query.criterion.FunctionCriterion;
import org.finalframework.data.query.criterion.operator.DefaultFunctionOperator;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 10:35:05
 * @since 1.0
 */
public interface JsonFunctionCriterion {
    static FunctionCriterion extract(@NonNull String path) {
        return new SingleFunctionCriterion<>(DefaultFunctionOperator.JSON_EXTRACT, path);
    }
}
