package org.ifinal.finalframework.data.query.criteriable;

import org.ifinal.finalframework.data.query.condition.BetweenCondition;
import org.ifinal.finalframework.data.query.condition.CompareCondition;
import org.ifinal.finalframework.data.query.condition.InCondition;
import org.ifinal.finalframework.data.query.condition.JsonCondition;
import org.ifinal.finalframework.data.query.condition.LikeCondition;
import org.ifinal.finalframework.data.query.condition.NullCondition;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Criteriable<V, R> extends NullCondition<R>, CompareCondition<V, R>, InCondition<V, R>,
        LikeCondition<R>, BetweenCondition<V, R>, JsonCondition<Object, R> {


}
