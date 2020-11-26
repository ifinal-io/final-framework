package org.ifinal.finalframework.data.query.criteriable;

import org.ifinal.finalframework.data.query.condition.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Criteriable<V, R> extends NullCondition<R>, CompareCondition<V, R>, InCondition<V, R>,
        LikeCondition<R>, BetweenCondition<V, R>, JsonCondition<Object, R> {


}
