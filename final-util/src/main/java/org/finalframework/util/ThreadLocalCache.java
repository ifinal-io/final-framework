package org.finalframework.util;

import lombok.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 14:06
 * @since 1.0
 */
final class ThreadLocalCache {
    private static final ThreadLocal<Map<String, Object>> cache = ThreadLocal.withInitial(LinkedHashMap::new);

    static void set(@NonNull String key, Object value) {
        cache.get().put(key, value);
    }

    static Object get(@NonNull String key) {
        return cache.get().get(key);
    }

    static boolean containsKey(@NonNull String key) {
        return cache.get().containsKey(key);
    }

    public static boolean containsValue(@NonNull Object value) {
        return cache.get().containsValue(value);
    }

    static Object remove(@NonNull String key) {
        return cache.get().remove(key);
    }

    static void remove() {
        cache.remove();
    }
}
