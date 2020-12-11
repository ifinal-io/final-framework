package org.ifinal.finalframework.util.collection;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Maps {


    private Maps() {
    }

    public static <K, V> MultiValueMap<K, V> multiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    public static <K, V> MultiValueMap<K, V> multiValueMap(int initialCapacity) {
        return new LinkedMultiValueMap<>(initialCapacity);
    }

    public static <K, V> Collection<Map<K, V>> combine(Map<K, Collection<V>> map) {
        Collection<Map<K, V>> result = new ArrayList<>();
        for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
            if (result.isEmpty()) {
                for (V value : entry.getValue()) {
                    Map<K, V> item = new HashMap<>();
                    item.put(entry.getKey(), value);
                    result.add(item);
                }
            } else {
                Collection<Map<K, V>> list = new ArrayList<>();
                for (Map<K, V> loop : result) {
                    for (V value : entry.getValue()) {
                        Map<K, V> item = new HashMap<>();
                        item.put(entry.getKey(), value);
                        item.putAll(loop);
                        list.add(item);
                    }
                }
                result = list;
            }
        }
        return result;
    }

}
