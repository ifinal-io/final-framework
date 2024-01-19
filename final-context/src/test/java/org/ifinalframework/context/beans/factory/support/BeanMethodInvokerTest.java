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

package org.ifinalframework.context.beans.factory.support;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author iimik
 * @version 1.3.1
 **/
@ExtendWith(MockitoExtension.class)
class BeanMethodInvokerTest {


    @Test
    void invokeDefault() {

        BeanMethodInvoker invoker = new BeanMethodInvoker() {
            @Override
            public Object invoke(String beanName, String methodName, Class<?>[] parameterTypes, Object[] args) {
                return null;
            }

            @Override
            public Object invoke(Class<?> beanType, String methodName, Class<?>[] parameterTypes, Object[] args) {
                return null;
            }

            @Override
            public Object invoke(Object bean, String methodName, Class<?>[] parameterTypes, Object[] args) {
                return methodName;
            }
        };

        Object value = invoker.invoke("haha", "hello", new Object[]{"haha"});
        assertEquals("hello", value);
    }

}