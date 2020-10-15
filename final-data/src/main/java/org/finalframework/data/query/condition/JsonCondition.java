package org.finalframework.data.query.condition;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-20 20:43:22
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface JsonCondition<V, R> extends ExecutableCondition {

    /**
     *
     */
    default R jsonContains(@NonNull V value) {
        return jsonContains(value, null);
    }

    /**
     *
     */
    R jsonContains(@NonNull V value, @Nullable String path);

    /**
     *
     */
    default R notJsonContains(@NonNull V value) {
        return notJsonContains(value, null);
    }

    /**
     *
     */
    R notJsonContains(@NonNull V value, @Nullable String path);

}
