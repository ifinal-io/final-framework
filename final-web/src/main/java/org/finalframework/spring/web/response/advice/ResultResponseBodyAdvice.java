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

package org.finalframework.spring.web.response.advice;

import org.finalframework.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringResponseBodyAdvice;
import org.finalframework.context.user.UserContextHolder;
import org.finalframework.spring.web.converter.Object2ResultConverter;
import org.finalframework.spring.web.interceptor.DurationHandlerInterceptor;
import org.finalframework.spring.web.interceptor.trace.TraceHandlerInterceptor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Duration;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-25 09:22:08
 * @see Object2ResultConverter
 * @since 1.0
 */
@Order(RestAdviceOrdered.RESULT_PRECEDENCE)
@RestControllerAdvice
@SpringResponseBodyAdvice
public class ResultResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    private static final Object2ResultConverter object2ResultConverter = new Object2ResultConverter();


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        final Result<?> result = object2ResultConverter.convert(body);
        if (result == null) {
            return null;
        }
        // set address
        result.setAddress(String.format("%s:%d", request.getLocalAddress().getAddress().getHostAddress(), request.getLocalAddress().getPort()));
        result.setLocale(LocaleContextHolder.getLocale());
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        result.setOperator(UserContextHolder.getUser());

        if (request instanceof ServletServerHttpRequest) {
            Long durationStart = (Long) ((ServletServerHttpRequest) request).getServletRequest()
                    .getAttribute(DurationHandlerInterceptor.DURATION_START_ATTRIBUTE);
            if (durationStart != null) {
                result.setDuration(Duration.ofMillis(System.currentTimeMillis() - durationStart));
            }
            String trace = (String) ((ServletServerHttpRequest) request).getServletRequest()
                    .getAttribute(TraceHandlerInterceptor.TRACE_ATTRIBUTE);
            result.setTrace(trace);
        }
        return result;
    }

}
