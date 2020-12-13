package org.ifinal.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface BetweenCondition<V, R> extends Condition {

    /**
     * @param min 下限值
     * @param max 上限值
     * @return result
     */
    R between(@NonNull V min, @NonNull V max);

    /**
     * @param min 下限值
     * @param max 上限值
     * @return result
     */
    R notBetween(@NonNull V min, @NonNull V max);

}
