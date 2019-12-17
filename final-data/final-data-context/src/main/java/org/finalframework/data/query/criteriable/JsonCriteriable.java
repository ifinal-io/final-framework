package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.condition.BetweenCondition;
import org.finalframework.data.query.condition.CompareCondition;
import org.finalframework.data.query.condition.InCondition;
import org.finalframework.data.query.condition.JsonFunctionCondition;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-16 17:51:26
 * @since 1.0
 */
public interface JsonCriteriable<T, V, R> extends JsonFunctionCondition<V, JsonCriteriable<T, V, R>>, CompareCondition<V, R>, BetweenCondition<V, R>, InCondition<V, R> {
    static <T, V, R> JsonCriteriable<T, V, R> extract(QProperty<T> property, String path) {
        return null;
    }
}
