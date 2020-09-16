package org.finalframework.util;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 14:09
 * @since 1.0
 */
@SuppressWarnings({"unchecked", "unused"})
public interface ThreadLocalUtils {

    static <T> T get(@NonNull String key, T defVal) {
        return ThreadLocalCache.containsKey(key) ? (T) ThreadLocalCache.get(key) : defVal;
    }

    static void set(@NonNull String key, Object value) {
        ThreadLocalCache.set(key, value);
    }

    static void setIfPresent(@NonNull String key, Object value) {
        if (ThreadLocalCache.containsKey(key)) {
            ThreadLocalCache.set(key, value);
        }
    }

    static void setIfNotPresent(@NonNull String key, Object value) {
        if (!ThreadLocalCache.containsKey(key)) {
            ThreadLocalCache.set(key, value);
        }
    }

    static Object get(@NonNull String key) {
        return ThreadLocalCache.get(key);
    }

    static Object remove(@NonNull String key) {
        return ThreadLocalCache.remove(key);
    }

    static void remove() {
        ThreadLocalCache.remove();
    }

}
