/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.data.mybatis.sql.util;

import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import org.apache.ibatis.builder.annotation.ProviderContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * ProviderContextUtil.
 *
 * @author iimik
 * @version 1.2.2
 * @since 1.2.2
 */
class ProviderContextUtil {

    private static final Constructor<ProviderContext> PROVIDER_CONTEXT_CONSTRUCTOR;

    private ProviderContextUtil() {
    }

    static {
        try {
            PROVIDER_CONTEXT_CONSTRUCTOR = ReflectionUtils.accessibleConstructor(ProviderContext.class,
                    Class.class, Method.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NonNull
    public static ProviderContext newContext(Class<?> mapper, Method method) {
        return newContext(mapper, method, null);
    }

    @NonNull
    public static ProviderContext newContext(Class<?> mapper, Method method, String databaseId) {
        try {
            return PROVIDER_CONTEXT_CONSTRUCTOR.newInstance(mapper, method, databaseId);
        } catch (Exception e) {
            throw new IllegalArgumentException("can not instance ProviderContext", e);
        }
    }

}
