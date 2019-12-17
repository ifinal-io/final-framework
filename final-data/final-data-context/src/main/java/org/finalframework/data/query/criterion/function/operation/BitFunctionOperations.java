package org.finalframework.data.query.criterion.function.operation;

import org.finalframework.data.query.criterion.function.FunctionOperations;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public interface BitFunctionOperations<T> extends FunctionOperations<T> {
    AndFunctionOperation<T> and();

    OrFunctionOperation<T> or();

    XorFunctionOperation<T> xor();

    NotFunctionOperation<T> not();
}
