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
package org.ifinal.finalframework.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * @author likly
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
     * @throws ClassNotFoundException if the class was not found.
     * @see ClassUtils#forName(String, ClassLoader)
     */
    public static Class<?> forName(@NonNull String name) throws ClassNotFoundException {
        return forName(name, null);
    }

    /**
     * return the class of name
     *
     * @param name        class name
     * @param classLoader class loader
     * @return class
     * @throws ClassNotFoundException if the class was not found
     */
    public static Class<?> forName(@NonNull String name, @Nullable ClassLoader classLoader) throws ClassNotFoundException {
        return ClassUtils.forName(name, classLoader);
    }


}
