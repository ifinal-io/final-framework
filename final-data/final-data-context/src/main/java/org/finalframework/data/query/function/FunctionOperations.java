package org.finalframework.data.query.function;

import org.finalframework.data.query.FunctionOperation;
import org.finalframework.data.query.FunctionOperator;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public interface FunctionOperations<T> {
    Class<T> type();

    FunctionOperation get(FunctionOperator operator);

    void register(FunctionOperation operation);

}
