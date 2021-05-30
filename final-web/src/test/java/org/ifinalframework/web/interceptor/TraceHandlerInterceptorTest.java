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

package org.ifinalframework.web.interceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import org.springframework.web.method.HandlerMethod;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.slf4j.MDC;

/**
 * TraceHandlerInterceptorTest.
 *
 * @author likly
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
