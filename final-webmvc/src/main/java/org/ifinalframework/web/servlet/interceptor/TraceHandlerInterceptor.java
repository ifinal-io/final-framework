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

import lombok.Setter;
import org.ifinalframework.core.generator.TraceGenerator;
import org.ifinalframework.core.generator.UuidTraceGenerator;
import org.ifinalframework.util.Asserts;
import org.ifinalframework.web.annotation.servlet.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author ilikly
 * @version 1.0.0
 * @see TraceGenerator
 * @see UuidTraceGenerator
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Interceptor
public class TraceHandlerInterceptor implements HandlerInterceptor {

    public static final String TRACE_ATTRIBUTE = TraceHandlerInterceptor.class.getCanonicalName() + ".trace";

    private static final Logger logger = LoggerFactory.getLogger(TraceHandlerInterceptor.class);

    private static final String TRACE = "trace";

    /**
     * TRACE 名称，放置到{@link MDC}中的KEY的名称
     */
    @Setter
    private String traceName = TRACE;

    /**
     * TRACE 参数名称
     */
    @Setter
    private String paramName = TRACE;

    /**
     * TRACE 请求头名称
     */
    @Setter
    private String headerName = TRACE;

    @Setter
    private TraceGenerator generator = new UuidTraceGenerator();

    @Override
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
                             final @NonNull Object handler) {
        // 获取 header 中是否有自定义的 trace
        String trace = null;
        if (Asserts.nonEmpty(paramName)) {
            trace = request.getParameter(paramName);
        }
        if (Asserts.isNull(trace) && Asserts.nonEmpty(headerName)) {
            trace = request.getHeader(headerName);

        }
        if (Asserts.isNull(trace)) {
            // 如果 header 中没有 trace，则获取 Request 域中的 trace 属性
            trace = (String) request.getAttribute(TRACE_ATTRIBUTE);
        }
        if (trace == null && generator != null) {
            // 如果 trace 还为空，则生成一个新的 trace
            trace = generator.generate();
        }
        request.setAttribute(TRACE_ATTRIBUTE, trace);
        MDC.put(traceName, trace);
        return true;
    }

    @Override
    public void afterCompletion(final @NonNull HttpServletRequest request, @NonNull final HttpServletResponse response,
                                final @NonNull Object handler,
                                @Nullable final Exception ex) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("remove trace from MDC context");
        }
        MDC.remove(traceName);
    }

}
