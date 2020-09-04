/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
