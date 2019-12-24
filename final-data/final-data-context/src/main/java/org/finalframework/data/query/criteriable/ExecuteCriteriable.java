package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.condition.BitFunctionCondition;
import org.finalframework.data.query.condition.DateFunctionCondition;
import org.finalframework.data.query.condition.JsonFunctionCondition;
import org.finalframework.data.query.condition.MathFunctionCondition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 23:09:27
 * @since 1.0
 */
public interface ExecuteCriteriable<V, R> extends
        DateFunctionCondition<DateCriteriable<R>>,
        MathFunctionCondition<V, NumberCriteriable<R>>,
        BitFunctionCondition<V, NumberCriteriable<R>>,
        JsonFunctionCondition<V, FunctionCriteriable<V, R>> {
}
