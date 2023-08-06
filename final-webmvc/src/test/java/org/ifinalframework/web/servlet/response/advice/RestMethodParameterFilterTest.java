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

package org.ifinalframework.web.servlet.response.advice;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ResponseBody;

import org.ifinalframework.web.servlet.response.annotation.ResponseIgnore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author ilikly
 * @version 1.3.1
 **/
@ExtendWith(MockitoExtension.class)
class RestMethodParameterFilterTest {

    @Test
    @SneakyThrows
    void matches() {

        Method method = ReflectionUtils.getRequiredMethod(getClass(), "matches");
        MethodParameter methodParameter = spy(new MethodParameter(method, -1));

        when(methodParameter.hasMethodAnnotation(ResponseIgnore.class)).thenReturn(true);
        assertFalse(RestMethodParameterFilter.INSTANCE.matches(methodParameter));

        when(methodParameter.hasMethodAnnotation(ResponseIgnore.class)).thenReturn(false);
        //        when(methodParameter.getDeclaringClass()).thenReturn(getClass());
        when(methodParameter.hasMethodAnnotation(ResponseBody.class)).thenReturn(true);
        assertTrue(RestMethodParameterFilter.INSTANCE.matches(methodParameter));
    }
}