/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.web.interceptor;


import org.finalframework.spring.annotation.factory.SpringHandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-05 21:55:37
 * @since 1.0
 */
@SpringHandlerInterceptor
public class DurationHandlerInterceptor implements AsyncHandlerInterceptor {

    public static final String DURATION_START_ATTRIBUTE = "org.finalframework.handler.duration.start";
    public static final String DURATION_END_ATTRIBUTE = "org.finalframework.handler.duration.end";
    public static final String DURATION_ATTRIBUTE = "org.finalframework.handler.duration";

    private static final Logger logger = LoggerFactory.getLogger(DurationHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        long durationStart = System.currentTimeMillis();
        request.setAttribute(DURATION_START_ATTRIBUTE, durationStart);
        logger.info("METHOD={},URI={},START={}", request.getMethod(), uri, durationStart);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long durationStart = (Long) request.getAttribute(DURATION_START_ATTRIBUTE);
        String uri = request.getRequestURI();
        long durationEnd = System.currentTimeMillis();
        long duration = durationEnd - durationStart;
        request.setAttribute(DURATION_END_ATTRIBUTE, durationEnd);
        request.setAttribute(DURATION_ATTRIBUTE, duration);
        logger.info("METHOD={},URI={},START={},DURATION={}", request.getMethod(), uri, durationEnd, duration);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}

