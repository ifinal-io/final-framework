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
public interface Criteriable<V, R> extends CompareCondition<V, R>, NullCondition<R>, InCondition<V, R>, LikeCondition<R>, BetweenCondition<V, R> {

    // date operator

    R dateEqual(@NonNull String date);

    R dateEqual(@NonNull LocalDateTime date);

    R dateEqual(@NonNull Date date);

    R dateEqual(@NonNull long date);

    R notDateEqual(@NonNull String date);

    R notDateEqual(@NonNull LocalDateTime date);

    R notDateEqual(@NonNull Date date);

    R notDateEqual(@NonNull long date);

    R dateBefore(@NonNull String date);

    R dateBefore(@NonNull LocalDateTime date);

    R dateBefore(@NonNull Date date);

    R dateBefore(@NonNull long date);

    R dateAfter(@NonNull String date);

    R dateAfter(@NonNull LocalDateTime date);

    R dateAfter(@NonNull Date date);

    R dateAfter(@NonNull long date);

    R dateBetween(@NonNull String min, @NonNull String max);

    R dateBetween(@NonNull LocalDateTime min, @NonNull LocalDateTime max);

    R dateBetween(@NonNull Date min, @NonNull Date max);

    R dateBetween(@NonNull long min, @NonNull long max);

    R notDateBetween(@NonNull String min, @NonNull String max);

    R notDateBetween(@NonNull LocalDateTime min, @NonNull LocalDateTime max);

    R notDateBetween(@NonNull Date min, @NonNull Date max);

    R notDateBetween(@NonNull long min, @NonNull long max);

    // math operator

    R maxEqual(@NonNull Number value);

    R maxNotEqual(@NonNull Number value);

    R maxGreaterThan(@NonNull Number value);

    R maxGreaterThanEqual(@NonNull Number value);

    R maxLessThan(@NonNull Number value);

    R maxLessThanEqual(@NonNull Number value);

    R maxIn(@NonNull Collection<Number> values);

    R maxNotIn(@NonNull Collection<Number> values);

    R maxBetween(@NonNull Number min, @NonNull Number max);

    R maxNotBetween(@NonNull Number min, @NonNull Number max);

    R minEqual(@NonNull Number value);

    R minNotEqual(@NonNull Number value);

    R minGreaterThan(@NonNull Number value);

    R minGreaterThanEqual(@NonNull Number value);

    R minLessThan(@NonNull Number value);

    R minLessThanEqual(@NonNull Number value);

    R minIn(@NonNull Collection<Number> values);

    R minNotIn(@NonNull Collection<Number> values);

    R minBetween(@NonNull Number min, @NonNull Number max);

    R minNotBetween(@NonNull Number min, @NonNull Number max);

    R sumEqual(@NonNull Number value);

    R sumNotEqual(@NonNull Number value);

    R sumGreaterThan(@NonNull Number value);

    R sumGreaterThanEqual(@NonNull Number value);

    R sumLessThan(@NonNull Number value);

    R sumLessThanEqual(@NonNull Number value);

    R sumIn(@NonNull Collection<Number> values);

    R sumNotIn(@NonNull Collection<Number> values);

    R sumBetween(@NonNull Number min, @NonNull Number max);

    R sumNotBetween(@NonNull Number min, @NonNull Number max);

    R avgEqual(@NonNull Number value);

    R avgNotEqual(@NonNull Number value);

    R avgGreaterThan(@NonNull Number value);

    R avgGreaterThanEqual(@NonNull Number value);

    R avgLessThan(@NonNull Number value);

    R avgLessThanEqual(@NonNull Number value);

    R avgIn(@NonNull Collection<Number> values);

    R avgNotIn(@NonNull Collection<Number> values);

    R avgBetween(@NonNull Number min, @NonNull Number max);

    R avgNotBetween(@NonNull Number min, @NonNull Number max);

}
