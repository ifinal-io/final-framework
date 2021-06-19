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

package org.ifinalframework.web.response.advice.result;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ifinalframework.context.converter.result.Object2ResultConverter;
import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.web.interceptor.DurationHandlerInterceptor;
import org.ifinalframework.web.interceptor.TraceHandlerInterceptor;
import org.ifinalframework.web.response.advice.RestResponseBodyAdvice;

import java.time.Duration;

/**
 * @author likly
 * @version 1.0.0
 * @see Object2ResultConverter
 * @since 1.0.0
 */
@Order(1000)
@RestControllerAdvice
public class ResultResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    private static final Object2ResultConverter object2ResultConverter = new Object2ResultConverter();

    @Override
    public Object doBeforeBodyWrite(final Object body, final MethodParameter returnType,
        final MediaType selectedContentType,
        final Class<? extends HttpMessageConverter<?>> selectedConverterType,
        final ServerHttpRequest request, final ServerHttpResponse response) {

        final Result<?> result = object2ResultConverter.convert(body);
        if (result == null) {
            return null;
        }
        // set address
        result.setAddress(request.getLocalAddress().getAddress().getHostName());
        result.setIp(String.format("%s:%d", request.getLocalAddress().getAddress().getHostAddress(),
            request.getLocalAddress().getPort()));
        // set locale
        result.setLocale(LocaleContextHolder.getLocale());
        // set timeZone
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        // set operator
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
