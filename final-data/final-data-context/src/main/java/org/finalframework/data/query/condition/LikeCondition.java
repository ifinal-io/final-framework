package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:24:29
 * @since 1.0
 */
public interface LikeCondition<R> extends Condition {
    R startWith(@NonNull String value);

    R notStartWith(@NonNull String value);

    R endWith(@NonNull String value);

    R notEndWith(@NonNull String value);

    R contains(@NonNull String value);

    R notContains(@NonNull String value);

    R like(@NonNull String value);

    R notLike(@NonNull String value);
}
