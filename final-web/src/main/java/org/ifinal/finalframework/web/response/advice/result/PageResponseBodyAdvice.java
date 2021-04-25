/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinal.finalframework.web.response.advice.result;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ifinal.finalframework.context.converter.result.Page2ResultConverter;
import org.ifinal.finalframework.web.response.advice.RestResponseBodyAdvice;

import com.github.pagehelper.Page;

/**
 * PageResponseBodyAdvice.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@ConditionalOnClass(Page.class)
public class PageResponseBodyAdvice implements RestResponseBodyAdvice<Object> {

    private static final Page2ResultConverter<?> converter = new Page2ResultConverter<>();

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable final Object body,
        final MethodParameter returnType, final MediaType selectedContentType,
        final Class<? extends HttpMessageConverter<?>> selectedConverterType, final ServerHttpRequest request,
        final ServerHttpResponse response) {

        if (body instanceof Page) {
            return converter.convert((Page) body);
        }

        return body;
    }

}
