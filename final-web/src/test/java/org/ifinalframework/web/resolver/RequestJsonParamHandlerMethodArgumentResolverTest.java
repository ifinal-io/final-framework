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

package org.ifinalframework.web.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.ifinalframework.json.Json;
import org.ifinalframework.web.annotation.bind.RequestJsonParam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

/**
 * RequestJsonParamHandlerMethodArgumentResolverTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class RequestJsonParamHandlerMethodArgumentResolverTest {

    @InjectMocks
    private RequestJsonParamHandlerMethodArgumentResolver resolver;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private ModelAndViewContainer modelAndViewContainer;

    @Mock
    private NativeWebRequest webRequest;

    @Mock
    private WebDataBinderFactory binderFactory;

    @Mock
    private RequestJsonParam requestJsonParam;

    @Test
    void supportsParameter() {
        when(methodParameter.hasParameterAnnotation(RequestJsonParam.class)).thenReturn(true);
        assertTrue(resolver.supportsParameter(methodParameter));
    }

    @Test
    void resolveArgumentFormRequestParameter() throws Exception {

        when(methodParameter.getParameterAnnotation(RequestJsonParam.class)).thenReturn(requestJsonParam);
        when(methodParameter.getGenericParameterType()).thenReturn(ResolvableType.forClass(Person.class).getType());
        when(methodParameter.getParameterName()).thenReturn("person");

        Person person = new Person("xiaoMing", 18);
        when(webRequest.getParameter("person")).thenReturn(Json.toJson(person));

        Object argument = resolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, binderFactory);

        assertTrue(argument instanceof Person);
        assertEquals(argument, person);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Person {

        private String name;

        private Integer age;

    }

}
