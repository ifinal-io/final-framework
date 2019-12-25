package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.function.BitFunction;
import org.finalframework.data.query.function.DateFunction;
import org.finalframework.data.query.function.JsonFunction;
import org.finalframework.data.query.function.MathFunction;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:09:27
 * @since 1.0
 */
public interface ExecuteCriteriable<V, R> extends
        DateFunction<FunctionCriteriable<V, R>>,
        MathFunction<V, FunctionCriteriable<V, R>>,
        BitFunction<V, FunctionCriteriable<V, R>>,
        JsonFunction<V, FunctionCriteriable<V, R>> {
}
