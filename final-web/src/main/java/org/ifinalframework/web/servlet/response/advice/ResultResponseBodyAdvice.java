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

package org.ifinalframework.web.servlet.response.advice;

import lombok.RequiredArgsConstructor;
import org.ifinalframework.context.result.ResultFunctionConsumerComposite;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.web.servlet.interceptor.DurationHandlerInterceptor;
import org.ifinalframework.web.servlet.interceptor.TraceHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
 * Wrap the {@link org.springframework.web.bind.annotation.ResponseBody} with {@link Result}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(1000)
@RequiredArgsConstructor
@RestControllerAdvice
@ConditionalOnBean(ResultFunctionConsumerComposite.class)
@ConditionalOnProperty(prefix = "final.web.response.advice", name = "result", havingValue = "true", matchIfMissing = true)
public class ResultResponseBodyAdvice implements RestResponseBodyAdvice<Object> {


    private final ResultFunctionConsumerComposite resultFunctionConsumerComposite;

    @Override
    public Object doBeforeBodyWrite(final Object body, final MethodParameter returnType,
                                    final MediaType selectedContentType,
                                    final Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                    final ServerHttpRequest request, final ServerHttpResponse response) {

        final Result<?> result = resultFunctionConsumerComposite.apply(body);
        if (result == null) {
            return null;
        }
        // set address
        result.setAddress(request.getLocalAddress().getAddress().getHostName());
        result.setIp(String.format("%s:%d", request.getLocalAddress().getAddress().getHostAddress(),
                request.getLocalAddress().getPort()));

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
