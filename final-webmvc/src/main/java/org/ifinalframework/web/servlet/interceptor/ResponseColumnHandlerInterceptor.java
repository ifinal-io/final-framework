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

import org.ifinalframework.core.result.Column;
import org.ifinalframework.web.annotation.response.ResponseColumn.ResponseColumns;
import org.ifinalframework.web.annotation.servlet.Interceptor;
import org.ifinalframework.web.servlet.response.ResponseHelper;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ResponseColumnHandlerInterceptor.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Interceptor
public class ResponseColumnHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final Object handler)
        throws Exception {

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).hasMethodAnnotation(ResponseColumns.class)) {
            ResponseColumns responseColumns = Objects
                .requireNonNull(((HandlerMethod) handler).getMethodAnnotation(ResponseColumns.class));
            ResponseHelper.setResponseColumn(request,
                Arrays.stream(responseColumns
                    .value())
                    .map(it -> new Column(it.key(), it.name(), it.value()))
                    .collect(Collectors.toList()));

        }

        return true;
    }

}
