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


import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.Page;
import org.finalframework.auto.spring.factory.annotation.SpringResponseBodyAdvice;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.finalframework.spring.web.response.view.PageJsonViewValue;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-12-06 15:56:23
 * @since 1.0
 */
@Order(RestAdviceOrdered.JSON_VIEW_PRECEDENCE)
@RestControllerAdvice
@SpringResponseBodyAdvice
public class JsonViewResponseBodyAdvice extends RestResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.supports(returnType, converterType) && returnType.hasMethodAnnotation(JsonView.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) return null;
        Class<?> view = getJsonView(returnType);

        if (body instanceof Page) {
            return new PageJsonViewValue((Page<?>) body, view);
        } else {
            return new JsonViewValue(body, view);
        }

    }

    private Class<?> getJsonView(MethodParameter returnType) {
        JsonView ann = returnType.getMethodAnnotation(JsonView.class);
        Assert.state(ann != null, "No JsonView annotation");

        Class<?>[] classes = ann.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for response body advice with exactly 1 class argument: " + returnType);
        }

        return classes[0];
    }
}

