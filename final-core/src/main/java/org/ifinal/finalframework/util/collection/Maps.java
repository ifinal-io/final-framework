package org.ifinal.finalframework.util.collection;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Maps.
 *
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

    public static <K, V> MultiValueMap<K, V> multiValueMap(final int initialCapacity) {
        return new LinkedMultiValueMap<>(initialCapacity);
    }

    public static <K, V> Collection<Map<K, V>> combine(final Map<K, Collection<V>> map) {
        Collection<Map<K, V>> result = new LinkedList<>();
        for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
            if (result.isEmpty()) {
                entry.getValue()
                    .stream()
                    .map(it -> {
                        final Map<K, V> item = new LinkedHashMap<>();
                        item.put(entry.getKey(), it);
                        return item;
                    })
                    .forEach(result::add);
            } else {
                final Collection<Map<K, V>> list = new LinkedList<>();
                for (Map<K, V> loop : result) {
                    for (V value : entry.getValue()) {
                        final Map<K, V> item = new LinkedHashMap<>();
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
