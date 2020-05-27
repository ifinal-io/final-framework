package org.finalframework.data.query.criterion;

import org.finalframework.data.query.criterion.function.operation.FunctionOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-27 15:35:46
 * @since 1.0
 */
@FunctionalInterface
public interface CriterionFunction {

    FunctionOperation apply();

}
