package org.finalframework.util;

import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-17 12:51:27
 * @since 1.0
 */
public interface MultiKeyMap<K1, K2, V> extends Map<K1, Map<K2, V>> {

    void add(K1 key1, K2 key2, @Nullable V value);

    default V get(K1 key1, K2 key2) {
        return getOrDefault(key1, key2, null);
    }

    V getOrDefault(K1 key1, K2 key2, V defValue);

}
