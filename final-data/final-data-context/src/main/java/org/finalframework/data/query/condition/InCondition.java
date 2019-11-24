package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:21:43
 * @since 1.0
 */
public interface InCondition<V, R> extends Condition {

    default R in(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R in(@NonNull Collection<V> values);


    default R nin(@NonNull V... values) {
        return in(Arrays.asList(values));
    }

    R nin(@NonNull Collection<V> values);

}
