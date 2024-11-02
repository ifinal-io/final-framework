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

package org.ifinalframework.web.servlet.interceptor;

import org.springframework.web.method.HandlerMethod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TraceHandlerInterceptorTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class TraceHandlerInterceptorTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private HandlerMethod handlerMethod;

    @InjectMocks
    private TraceHandlerInterceptor traceHandlerInterceptor;

    @Test
    void preHandle() throws Exception {
        traceHandlerInterceptor.preHandle(httpServletRequest, httpServletResponse, handlerMethod);
        assertNotNull(MDC.get("trace"));
        traceHandlerInterceptor.afterCompletion(httpServletRequest, httpServletResponse, handlerMethod, null);
        assertNull(MDC.get("trace"));
    }

    @Test
    void preHandleFromParam() {

        String trace = UUID.randomUUID().toString();
        when(httpServletRequest.getParameter("trace")).thenReturn(trace);

        traceHandlerInterceptor.preHandle(httpServletRequest, httpServletResponse, handlerMethod);
        assertNotNull(MDC.get("trace"));
        assertEquals(trace, MDC.get("trace"));
    }

}
