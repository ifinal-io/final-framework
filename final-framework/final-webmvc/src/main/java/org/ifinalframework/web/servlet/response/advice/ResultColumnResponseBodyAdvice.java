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

import org.springframework.context.expression.MapAccessor;
import org.springframework.core.MethodParameter;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.ifinalframework.context.expression.Spel;
import org.ifinalframework.core.result.Column;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.web.servlet.response.ResponseHelper;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ResultColumnResponseBodyAdvice.
 *
 * @author iimik
 * @version 1.0.0
 * @see ResponseHelper
 * @see org.ifinalframework.web.servlet.interceptor.ResponseColumnHandlerInterceptor
 * @since 1.0.0
 */
@RestControllerAdvice
public class ResultColumnResponseBodyAdvice implements AbsResultResponseBodyAdvice<Result<List<?>>> {

    public static final MapAccessor ACCESSOR = new MapAccessor();

    @Nullable
    @Override
    public Result<List<?>> doBeforeBodyWrite(@Nullable final Result<List<?>> body,
                                             @NonNull final MethodParameter returnType,
                                             @NonNull final MediaType selectedContentType,
                                             @NonNull final Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             @NonNull final ServerHttpRequest request,
                                             @NonNull final ServerHttpResponse response) {

        if (Objects.isNull(body)) {
            return null;
        }

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        List<Column> responseColumn = ResponseHelper.getResponseColumn(servletRequest);

        if (CollectionUtils.isEmpty(responseColumn)) {
            return body;
        }

        body.setHeader(responseColumn);

        if (!CollectionUtils.isEmpty(body.getData())) {

            final List<Map<String, Object>> list = new ArrayList<>(body.getData().size());

            for (final Object data : body.getData()) {
                Map<String, Object> item = new LinkedHashMap<>();

                StandardEvaluationContext context = new StandardEvaluationContext(data);
                context.addPropertyAccessor(ACCESSOR);

                for (final Column column : responseColumn) {
                    item.put(column.getKey(), Spel.getValue(column.getValue(), context));
                }

                list.add(item);
            }

            body.setData(list);
        }

        return body;
    }

}
