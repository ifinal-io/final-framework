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

package org.ifinalframework.data.web.servlet.method.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

import org.ifinalframework.data.domain.DomainService;
import org.ifinalframework.data.domain.DomainServiceRegistry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.Collections;

import lombok.SneakyThrows;

import static org.mockito.Mockito.*;


/**
 * DomainServiceHandlerMethodArgumentResolverTest.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
@ExtendWith(MockitoExtension.class)
class DomainServiceHandlerMethodArgumentResolverTest {

    @Mock
    private DomainServiceRegistry domainServiceRegistry;
    @Mock
    private NativeWebRequest nativeWebRequest;
    @Mock
    private DomainService domainService;
    @InjectMocks
    private DomainServiceHandlerMethodArgumentResolver domainServiceHandlerMethodArgumentResolver;

    void domainServiceMethod(DomainService domainService) {

    }

    @Test
    void supportsParameter() {
    }

    @Test
    @SneakyThrows
    void resolveArgument() {


        final Method method = ReflectionUtils.getRequiredMethod(getClass(), "domainServiceMethod", DomainService.class);
        final MethodParameter methodParameter = MethodParameter.forExecutable(method, 0);

        when(nativeWebRequest.getAttribute(anyString(), anyInt())).thenReturn(Collections.singletonMap("resource", "domainResource"));
        when(domainServiceRegistry.getDomainService("domainResource")).thenReturn(domainService);


        final Object argument = domainServiceHandlerMethodArgumentResolver.resolveArgument(methodParameter, null, nativeWebRequest, null);
        Assertions.assertNotNull(argument);
        Assertions.assertEquals(domainService, argument);
    }
}