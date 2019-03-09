package com.ilikly.finalframework.data.query;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:47
 * @since 1.0
 */
@SuppressWarnings("all")
public interface Criteriable<V, R> {

    //    Comparable operator

    R eq(@NonNull V value);

    R neq(@NonNull V value);

    R gt(@NonNull V value);

    R gte(@NonNull V value);

    R lt(@NonNull V value);

    R lte(@NonNull V value);

    //    in operator

    R in(@NonNull V... values);

    R in(@NonNull Collection<V> values);

    R nin(@NonNull V... values);

    R nin(@NonNull Collection<V> values);

    R isNull();

    R nonNull();

    //    like operator

    R startWith(@NonNull String value);

    R notStartWith(@NonNull String value);

    R endWith(@NonNull String value);

    R notEndWith(@NonNull String value);

    R contains(@NonNull String value);

    R notContains(@NonNull String value);

    R like(@NonNull String value);

    R notLike(@NonNull String value);

    // date operator

    R before(@NonNull Date date);

    R before(@NonNull long date);

    R after(@NonNull Date date);

    R after(@NonNull long date);

    R dateEqual(@NonNull Date date);

    R notDateEqual(@NonNull Date date);

    R dateEqual(@NonNull long date);

    R notDateEqual(@NonNull long date);

    R dateBefore(@NonNull Date date);

    R dateBefore(@NonNull long date);

    R dateAfter(@NonNull Date date);

    R dateAfter(@NonNull long date);

    R between(@NonNull V min, @NonNull V max);

    R notBetween(@NonNull V min, @NonNull V max);

    R dateBetween(@NonNull Date min, @NonNull Date max);

    R notDateBetween(@NonNull Date min, @NonNull Date max);

    R dateBetween(@NonNull long min, @NonNull long max);

    R notDateBetween(@NonNull long min, @NonNull long max);

}
