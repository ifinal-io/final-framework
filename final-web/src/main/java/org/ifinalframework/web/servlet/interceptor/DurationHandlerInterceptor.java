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

import org.ifinalframework.web.annotation.servlet.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Interceptor
public class DurationHandlerInterceptor implements AsyncHandlerInterceptor {

    private static final String DURATION_ATTRIBUTE_PREFIX = DurationHandlerInterceptor.class.getCanonicalName();

    public static final String DURATION_START_ATTRIBUTE = String.join(".", DURATION_ATTRIBUTE_PREFIX, "start");

    public static final String DURATION_END_ATTRIBUTE = String.join(".", DURATION_ATTRIBUTE_PREFIX, "end");

    public static final String DURATION_ATTRIBUTE = String.join(".", DURATION_ATTRIBUTE_PREFIX, "duration");

    private static final Logger logger = LoggerFactory.getLogger(DurationHandlerInterceptor.class);

    @Override
    public boolean preHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
                             final @NonNull Object handler) {

        String uri = request.getRequestURI();
        long durationStart = System.currentTimeMillis();
        request.setAttribute(DURATION_START_ATTRIBUTE, durationStart);
        if (logger.isDebugEnabled()) {
            logger.debug("METHOD={},URI={},START={}", request.getMethod(), uri, durationStart);
        }

        return true;
    }

    @Override
    public void postHandle(final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
                           final @NonNull Object handler,
                           final ModelAndView modelAndView) {

        Long durationStart = (Long) request.getAttribute(DURATION_START_ATTRIBUTE);
        String uri = request.getRequestURI();
        long durationEnd = System.currentTimeMillis();
        long duration = durationEnd - durationStart;
        request.setAttribute(DURATION_END_ATTRIBUTE, durationEnd);
        request.setAttribute(DURATION_ATTRIBUTE, duration);
        if (logger.isDebugEnabled()) {
            logger.debug("METHOD={},URI={},START={},DURATION={}", request.getMethod(), uri, durationEnd, duration);
        }

    }

}

