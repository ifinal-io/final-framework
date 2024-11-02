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

package org.ifinalframework.data.cache;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;

import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.cache.annotation.Cache;
import org.ifinalframework.context.expression.MethodMetadata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import static org.mockito.Mockito.*;

/**
 * CacheDelInterceptorHandlerTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@ExtendWith(MockitoExtension.class)
class CacheDelInterceptorHandlerTest {

    private final CacheDelInterceptorHandler handler = new CacheDelInterceptorHandler();

    @Mock
    private Cache cache;
    @Mock
    private InvocationContext invocationContext;
    @Mock
    private MethodMetadata methodMetadata;


    @Test
    void handle() {


        final Method method = ReflectionUtils.findMethod(CacheDelInterceptorHandlerTest.class, "handle");
        when(methodMetadata.getMethod()).thenReturn(method);
        when(methodMetadata.getTargetMethod()).thenReturn(method);

        when(invocationContext.metadata()).thenReturn(methodMetadata);

        Class targetClass = CacheDelInterceptorHandlerTest.class;
        when(methodMetadata.getTargetClass()).thenReturn(targetClass);
        when(methodMetadata.getMethodKey()).thenReturn(new AnnotatedElementKey(Object.class, Object.class));

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("key", new String[]{"key"});
        map.put("field", new String[]{});
        map.put("delimiter", ":");
        map.put("condition", "");
        AnnotationAttributes annotationAttributes = new AnnotationAttributes(map);
        Object result = 1;
        handler.handle(cache, invocationContext, annotationAttributes, result, null);
    }
}