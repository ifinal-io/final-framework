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

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author iimik
 * @version 1.0.0
 * @see ResultResponseBodyAdvice
 * @see org.springframework.web.bind.annotation.ResponseBody
 * @since 1.0.0
 */
public interface RestResponseBodyAdvice<T> extends ResponseBodyAdvice<T> {

    @Override
    default boolean supports(final @NonNull MethodParameter methodParameter,
                             final @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return DefaultResponseBodyMethodParameterPredicate.INSTANCE.test(methodParameter);
    }

    @Nullable
    @Override
    default T beforeBodyWrite(@Nullable T body, MethodParameter returnType, MediaType selectedContentType,
                              Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                              ServerHttpResponse response) {

        if (request.getURI().getPath().contains("swagger")) {
            return body;
        }

        return doBeforeBodyWrite(body, returnType, selectedContentType, selectedConverterType, request, response);

    }

    T doBeforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
                        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                        ServerHttpResponse response);

}

