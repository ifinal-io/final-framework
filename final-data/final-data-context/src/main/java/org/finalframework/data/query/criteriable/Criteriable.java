package org.finalframework.data.query.criteriable;

import org.finalframework.data.query.condition.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Criteriable<V, R> extends NullCondition<R>, CompareCondition<V, R>, InCondition<V, R>, LikeCondition<R>, BetweenCondition<V, R> {


}
