/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.util;

import lombok.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"unchecked", "unused"})
public interface ThreadLocals {

    static <T> T get(@NonNull String key, T defVal) {
        return ThreadContext.containsKey(key) ? (T) ThreadContext.get(key) : defVal;
    }

    static Object get(@NonNull String key) {
        return ThreadContext.get(key);
    }

    static void set(@NonNull String key, Object value) {
        ThreadContext.set(key, value);
    }

    static void setIfPresent(@NonNull String key, Object value) {
        if (ThreadContext.containsKey(key)) {
            ThreadContext.set(key, value);
        }
    }

    static void setIfNotPresent(@NonNull String key, Object value) {
        if (!ThreadContext.containsKey(key)) {
            ThreadContext.set(key, value);
        }
    }

    static Object remove(@NonNull String key) {
        return ThreadContext.remove(key);
    }

    static void remove() {
        ThreadContext.remove();
    }

}
