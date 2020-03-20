package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.condition.BetweenCondition;
import org.finalframework.data.query.condition.CompareCondition;
import org.finalframework.data.query.condition.InCondition;
import org.finalframework.data.query.condition.JsonCondition;
import org.finalframework.data.query.condition.LikeCondition;
import org.finalframework.data.query.condition.NullCondition;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
public interface Criteriable<V, R> extends NullCondition<R>, CompareCondition<V, R>, InCondition<V, R>,
    LikeCondition<R>, BetweenCondition<V, R>, JsonCondition<Object, R> {


}
