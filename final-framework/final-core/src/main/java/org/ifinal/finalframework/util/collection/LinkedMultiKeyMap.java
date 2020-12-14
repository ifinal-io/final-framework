package org.ifinal.finalframework.util.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedMultiKeyMap.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LinkedMultiKeyMap<K1, K2, V> extends LinkedHashMap<K1, Map<K2, V>> implements MultiKeyMap<K1, K2, V> {

    @Override
    public void put(final K1 key1, final K2 key2, final V value) {
        final Map<K2, V> map = computeIfAbsent(key1, k -> new LinkedHashMap<>());
        map.put(key2, value);
    }

    @Override
    public V getOrDefault(final K1 key1, final K2 key2, final V defValue) {
        final Map<K2, V> map = get(key1);
        return map == null ? defValue : map.getOrDefault(key2, defValue);
    }

}

