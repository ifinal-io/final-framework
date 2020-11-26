package org.ifinal.finalframework.util.collection;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Maps {

    static <K, V> MultiValueMap<K, V> multiValueMap() {
        return new LinkedMultiValueMap<>();
    }

    static <K, V> MultiValueMap<K, V> multiValueMap(int initialCapacity) {
        return new LinkedMultiValueMap<>(initialCapacity);
    }

}
