package org.ifinal.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface CompareCondition<V, R> extends Condition {
    R eq(@NonNull V value);

    R neq(@NonNull V value);

    R gt(@NonNull V value);

    R gte(@NonNull V value);

    R lt(@NonNull V value);

    R lte(@NonNull V value);

    default R before(@NonNull V value) {
        return lt(value);
    }

    default R after(@NonNull V value) {
        return gt(value);
    }
}
