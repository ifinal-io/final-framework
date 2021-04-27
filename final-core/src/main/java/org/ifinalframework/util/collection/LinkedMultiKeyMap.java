/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.util.collection;

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

