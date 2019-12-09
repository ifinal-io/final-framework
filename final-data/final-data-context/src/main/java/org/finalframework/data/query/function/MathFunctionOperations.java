package org.finalframework.data.query.function;

import org.finalframework.data.query.function.operation.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:31:57
 * @since 1.0
 */
public interface MathFunctionOperations<T> extends FunctionOperations<T> {
    MaxFunctionOperation max();

    MinFunctionOperation min();

    SumFunctionOperation sum();

    AvgFunctionOperation avg();
}
