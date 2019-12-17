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
public interface Executable<T, V, R> extends
        DateFunctionCondition<DateCriteriable<V, R>>,
        BitFunctionCondition<V, NumberCriteriable<T, Number, R>>,
        MathFunctionCondition<V, NumberCriteriable<T, Number, R>>,
        JsonFunctionCondition<T, JsonCriteriable<T, Object, R>> {
}
