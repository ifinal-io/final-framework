package org.ifinal.finalframework.util;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
final class ThreadLocalCache {

    private static final ThreadLocal<Map<String, Object>> cache = ThreadLocal.withInitial(LinkedHashMap::new);

    private ThreadLocalCache() {
    }

    static void set(final @NonNull String key, final Object value) {
        cache.get().put(key, value);
    }

    static Object get(final @NonNull String key) {
        return cache.get().get(key);
    }

    static boolean containsKey(final @NonNull String key) {
        return cache.get().containsKey(key);
    }

    public static boolean containsValue(final @NonNull Object value) {
        return cache.get().containsValue(value);
    }

    static Object remove(final @NonNull String key) {
        return cache.get().remove(key);
    }

    static void remove() {
        cache.remove();
    }

}
