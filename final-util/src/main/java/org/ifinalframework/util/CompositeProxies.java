/*
 * Copyright 2020-2023 the original author or authors.
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;

/**
 * CompositeProxies.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
@RequiredArgsConstructor
public class CompositeProxies {

    @SuppressWarnings("unchecked")
    public static <T> T composite(Class<T> clazz, List<T> list) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyInvocationHandler<>(list));
    }

    @RequiredArgsConstructor
    private static final class ProxyInvocationHandler<T> implements InvocationHandler {
        private final List<T> targets;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (CollectionUtils.isEmpty(targets)) {
                return null;
            }

            Object result = null;

            for (final T target : targets) {
                result = method.invoke(target, args);
            }

            return result;
        }
    }


}
