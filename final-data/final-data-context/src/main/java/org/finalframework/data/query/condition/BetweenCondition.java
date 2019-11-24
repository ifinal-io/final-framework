package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:52:45
 * @since 1.0
 */
public interface BetweenCondition<V, R> extends Condition {
    R between(@NonNull V min, @NonNull V max);

    R notBetween(@NonNull V min, @NonNull V max);
}
