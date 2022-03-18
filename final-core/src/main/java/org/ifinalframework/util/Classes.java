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

package org.ifinalframework.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.Objects;

/**
 * @author ilikly
 * @version 1.0.0
 * @see org.springframework.util.ClassUtils
 * @since 1.0.0
 */
public final class Classes {

    private Classes() {
    }

    /**
     * @param name class name
     * @return class
     * @see ClassUtils#forName(String, ClassLoader)
     */
    @Nullable
    public static Class<?> forName(final @NonNull String name) {
        return forName(name, null);
    }

    /**
     * return the class of name
     *
     * @param name        class name
     * @param classLoader class loader
     * @return class
     */
    @Nullable
    public static Class<?> forName(final @NonNull String name, final @Nullable ClassLoader classLoader) {
        try {
            Objects.requireNonNull(name, "clazz name can not be null");
            return ClassUtils.forName(name, classLoader);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> requiredForName(final @NonNull String name) {
        return requiredForName(name, null);
    }

    @NonNull
    public static Class<?> requiredForName(final @NonNull String name, final @Nullable ClassLoader classLoader) {
        Class<?> clazz = forName(name, classLoader);
        Objects.requireNonNull(clazz, "can not found class of name = " + name);
        return clazz;
    }

}
