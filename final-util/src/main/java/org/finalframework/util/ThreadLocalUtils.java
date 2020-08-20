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
