

package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-24 00:24:29
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface LikeCondition<R> extends Condition {
    default R startWith(@NonNull String value) {
        return like("%" + value);
    }

    default R notStartWith(@NonNull String value) {
        return notLike("%" + value);
    }

    default R endWith(@NonNull String value) {
        return like(value + "%");
    }

    default R notEndWith(@NonNull String value) {
        return notLike(value + "%");
    }

    default R contains(@NonNull String value) {
        return like("%" + value + "%");
    }

    default R notContains(@NonNull String value) {
        return notLike("%" + value + "%");
    }

    R like(@NonNull String value);

    R notLike(@NonNull String value);

}
