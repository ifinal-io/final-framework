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

package org.ifinalframework.validation.beanvalidation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.validation.annotation.Validated;

import org.ifinalframework.validation.GlobalValidationGroupsProvider;
import org.ifinalframework.validation.MethodValidationGroupsProvider;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * FinalMethodValidationInterceptorTest.
 *
 * @author ilikly
 * @version 1.5.0
 * @since 1.5.0
 */
@ExtendWith(MockitoExtension.class)
class FinalMethodValidationInterceptorTest {

    @Spy
    private FinalMethodValidationInterceptor finalMethodValidationInterceptor;

    @Mock
    private MethodValidationGroupsProvider methodValidationGroupsProvider;
    @Mock
    private GlobalValidationGroupsProvider globalValidationGroupsProvider;

    @Validated
    void testMethod() {

    }

    @Test
    void determineValidationGroups() {
        final Method testMethod = ReflectionUtils.findMethod(this.getClass(), "testMethod").get();
        final MethodInvocation methodInvocation = new MethodInvocation() {

            @Override
            public Object proceed() throws Throwable {
                return null;
            }

            @Override
            public Object getThis() {
                return this;
            }

            @Override
            public AccessibleObject getStaticPart() {
                return null;
            }

            @Override
            public Object[] getArguments() {
                return new Object[0];
            }

            @Override
            public Method getMethod() {
                return testMethod;
            }
        };
        Class<?>[] classes = finalMethodValidationInterceptor.determineValidationGroups(methodInvocation);
        assertNotNull(classes);
        assertEquals(0, classes.length);


        // method

        finalMethodValidationInterceptor.setMethodValidationGroupsProvider(methodValidationGroupsProvider);
        when(methodValidationGroupsProvider.getValidationGroups(methodInvocation)).thenReturn(Arrays.asList(Object.class));
        classes =finalMethodValidationInterceptor.determineValidationGroups(methodInvocation);

        assertEquals(1,classes.length);
        assertEquals(Object.class,classes[0]);

        // global
        finalMethodValidationInterceptor.setGlobalValidationGroupsProvider(globalValidationGroupsProvider);
        when(globalValidationGroupsProvider.getValidationGroups(null)).thenReturn(Collections.singletonList(Class.class));
        classes =finalMethodValidationInterceptor.determineValidationGroups(methodInvocation);
        assertEquals(2,classes.length);
        assertEquals(Class.class,classes[0]);
        assertEquals(Object.class,classes[1]);

    }
}