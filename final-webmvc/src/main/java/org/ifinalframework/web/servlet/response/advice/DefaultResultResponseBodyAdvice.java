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

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import org.ifinalframework.context.result.ResultFunctionConsumerComposite;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.web.servlet.interceptor.DurationHandlerInterceptor;
import org.ifinalframework.web.servlet.interceptor.TraceHandlerInterceptor;

import java.time.Duration;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 将{@link ResponseBody}标记的返回结果包装成{@link Result}。
 *
 * @author iimik
 * @version 1.0.0
 * @see org.ifinalframework.http.converter.JsonStringHttpMessageConverter
 * @see org.ifinalframework.web.servlet.config.JsonStringHttpMessageConverterWebMvcConfigurer
 * @since 1.0.0
 */
@Order(1000)
@RequiredArgsConstructor
@RestControllerAdvice
@ConditionalOnBean(ResultFunctionConsumerComposite.class)
@ConditionalOnProperty(prefix = "final.web.response.advice", name = "result", havingValue = "true", matchIfMissing = true)
public class DefaultResultResponseBodyAdvice extends SmartResponseBodyAdvice<Object,Result<?>> {


    private final ResultFunctionConsumerComposite resultFunctionConsumerComposite;
    private final List<ResponseBodyAdviceExcludePredicate> responseBodyAdviceExcludePredicates;

    @Override
    public boolean supports(final @NonNull MethodParameter methodParameter,
                             final @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return DefaultResponseBodyMethodParameterPredicate.INSTANCE.test(methodParameter);
    }

    @Override
    protected boolean exclude(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        for (ResponseBodyAdviceExcludePredicate predicate : responseBodyAdviceExcludePredicates) {
            if(predicate.test(body, returnType, selectedContentType, selectedConverterType, request, response)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected Result<?> doBeforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final Result<?> result = resultFunctionConsumerComposite.apply(body);
        if (result == null) {
            return null;
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
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
