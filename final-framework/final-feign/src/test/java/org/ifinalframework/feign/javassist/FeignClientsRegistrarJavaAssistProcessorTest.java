/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.feign.javassist;

import javassist.ClassPool;

import org.springframework.util.ReflectionUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FeignClientsRegistrarJavaAssistProcessorTest
 *
 * @author iimik
 * @since 1.6.0
 **/
class FeignClientsRegistrarJavaAssistProcessorTest {

    @Test
    void process() throws Throwable {

        final ClassPool pool = ClassPool.getDefault();
        new FeignClientsRegistrarJavaAssistProcessor().process(pool);

        Class clazz = Class.forName("org.springframework.cloud.openfeign.FeignClientsRegistrar");
        final Method getClientName = ReflectionUtils.findMethod(clazz, "getClientName", Map.class);
        getClientName.setAccessible(true);
        final Constructor constructor = ReflectionUtils.accessibleConstructor(clazz);
        constructor.setAccessible(true);
        final Object instance = constructor.newInstance();

        Assertions.assertDoesNotThrow( () -> getClientName.invoke(instance, new HashMap<>()));


        final Object clientName = getClientName.invoke(instance, new HashMap<>());

    }
}