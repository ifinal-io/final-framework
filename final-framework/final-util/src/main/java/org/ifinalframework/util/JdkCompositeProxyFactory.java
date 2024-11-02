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

import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 通过JDK代理，生产某个类（一般为接口）的复合代码对象。
 *
 * @author iimik
 * @version 1.6.0
 * @since 1.6.0
 */
public class JdkCompositeProxyFactory implements Proxies.CompositeProxyFactory {

    @Override
    public <T> T create(Class<T> clazz, List<T> list) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyInvocationHandler<>(list));
    }

    private record ProxyInvocationHandler<T>(List<T> targets) implements InvocationHandler {
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
