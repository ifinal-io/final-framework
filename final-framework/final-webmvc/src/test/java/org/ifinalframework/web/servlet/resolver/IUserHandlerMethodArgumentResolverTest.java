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

package org.ifinalframework.web.servlet.resolver;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestBody;

import org.ifinalframework.context.user.DefaultUserSupplier;
import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.context.user.UserSupplier;
import org.ifinalframework.data.annotation.AbsUser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * IUserHandlerMethodArgumentResolverTest.
 *
 * @author iimik
 * @version 1.4.1
 * @since 1.4.1
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class IUserHandlerMethodArgumentResolverTest {

    @Mock
    private ObjectProvider<UserSupplier> provider;
    @Mock
    private MethodParameter methodParameter;

    @Test
    void supportsParameter() {
        UserContextHolder.reset();
        Class clazz = AbsUser.class;
        Mockito.when(provider.orderedStream()).thenReturn(Stream.of(new DefaultUserSupplier()));
        IUserHandlerMethodArgumentResolver resolver = new IUserHandlerMethodArgumentResolver(provider);
        Mockito.when(methodParameter.getParameterType()).thenReturn(clazz);
        Assertions.assertTrue(resolver.supportsParameter(methodParameter));

        Mockito.when(methodParameter.hasParameterAnnotation(RequestBody.class)).thenReturn(true);
        Assertions.assertFalse(resolver.supportsParameter(methodParameter));


    }

    @Test
    @SneakyThrows
    void resolveArgument() {
        UserContextHolder.reset();
        AbsUser user = new AbsUser();
        UserContextHolder.setUser(user);
        Mockito.when(provider.orderedStream()).thenReturn(Stream.of(new DefaultUserSupplier()));
        IUserHandlerMethodArgumentResolver resolver = new IUserHandlerMethodArgumentResolver(provider);
        Object argument = resolver.resolveArgument(methodParameter, null, null, null);
        Assertions.assertEquals(user, argument);

    }
}