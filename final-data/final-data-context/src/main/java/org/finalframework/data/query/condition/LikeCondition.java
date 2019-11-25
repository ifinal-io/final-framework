package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:24:29
 * @since 1.0
 */
public interface LikeCondition<R> extends Condition {
    default R startWith(@NonNull String value) {
        return like(null, value, "%");
    }

    default R notStartWith(@NonNull String value) {
        return notLike(null, value, "%s");
    }

    default R endWith(@NonNull String value) {
        return like("%", value, null);
    }

    default R notEndWith(@NonNull String value) {
        return notLike("%", value, null);
    }

    default R contains(@NonNull String value) {
        return like("%", value, "%");
    }

    default R notContains(@NonNull String value) {
        return notLike("%", value, "%");
    }

    default R like(@NonNull String value) {
        return contains(value);
    }

    default R notLike(@NonNull String value) {
        return notContains(value);
    }

    R like(@Nullable String prefix, @NonNull String value, @Nullable String suffix);

    R notLike(@Nullable String prefix, @NonNull String value, @Nullable String suffix);
}
