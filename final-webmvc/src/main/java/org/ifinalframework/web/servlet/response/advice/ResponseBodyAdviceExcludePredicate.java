/*
 * Copyright 2020-2024 the original author or authors.
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
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * {@link ResponseBodyAdvice}排除器。
 * <h4>功能</h4>
 * 对{@link ResponseBodyAdvice#supports(MethodParameter, Class)}的补充，解决原生方法不支持动态判断是否支持{@code @ResponseBody}标记的方法的改写，如{@code Swagger}内置的URI是不应该被改写的。
 * <h4>用法</h4>
 * 该排除器应该在{@code beforeBodyWrite} 方法执行的最前面调用，用于动态判断该{@code ResponseBodyAdvice}是否支持对{@code @ResponseBody}的改写。
 *
 * @author iimik
 * @see ResponseBodyAdvice
 * @see SmartResponseBodyAdvice
 * @see DefaultResultResponseBodyAdvice
 * @since 1.6.0.
 **/
@FunctionalInterface
public interface ResponseBodyAdviceExcludePredicate<T> {
    /**
     * 在{@link ResponseBodyAdvice#beforeBodyWrite(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)}方法执行时执行，如果被排除，则应该直接返回{@code body}。
     *
     * @see ResponseBodyAdvice#beforeBodyWrite(Object, MethodParameter, MediaType, Class, ServerHttpRequest, ServerHttpResponse)
     */
    boolean exclude(@Nullable T body, MethodParameter returnType, MediaType selectedContentType,
                    Class<? extends HttpMessageConverter<?>> selectedConverterType,
                    ServerHttpRequest request, ServerHttpResponse response);
}
