package org.finalframework.data.query;

import org.finalframework.data.query.condition.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Criteriable<V, R> extends CompareCondition<V, R>, NullCondition<R>, InCondition<V, R>, LikeCondition<R>, BetweenCondition<V, R> {


    // date operator

    R dateEqual(@NonNull LocalDateTime date);

    R dateEqual(@NonNull Date date);

    R dateEqual(@NonNull long date);

    R notDateEqual(@NonNull LocalDateTime date);

    R notDateEqual(@NonNull Date date);

    R notDateEqual(@NonNull long date);

    R dateBefore(@NonNull LocalDateTime date);

    R dateBefore(@NonNull Date date);

    R dateBefore(@NonNull long date);

    R dateAfter(@NonNull LocalDateTime date);

    R dateAfter(@NonNull Date date);

    R dateAfter(@NonNull long date);

    R dateBetween(@NonNull LocalDateTime min, @NonNull LocalDateTime max);

    R dateBetween(@NonNull Date min, @NonNull Date max);

    R notDateBetween(@NonNull LocalDateTime min, @NonNull LocalDateTime max);

    R notDateBetween(@NonNull Date min, @NonNull Date max);

    R dateBetween(@NonNull long min, @NonNull long max);

    R notDateBetween(@NonNull long min, @NonNull long max);

}
