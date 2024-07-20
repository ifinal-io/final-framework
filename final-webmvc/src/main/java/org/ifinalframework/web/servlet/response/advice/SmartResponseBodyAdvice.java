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
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 智能{@link org.springframework.web.bind.annotation.ResponseBody}切面。
 *
 * @author iimik
 * @since 1.6.0
 **/
public abstract class SmartResponseBodyAdvice<S, T> implements ResponseBodyAdvice<Object> {


    /**
     * {@link #supports(MethodParameter, Class)}方法只能从参数定义层面判断是否能够支持，在一些特定的情况下无法判断，如根据{@link ServerHttpRequest#getURI()}进行判断。
     *
     * @see #supports(MethodParameter, Class)
     * @see ResponseBodyAdviceExcludePredicate
     */
    protected abstract boolean exclude(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response);


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(exclude(body, returnType, selectedContentType, selectedConverterType, request, response)){
            return body;
        }
        return doBeforeBodyWrite((S) body,returnType,selectedContentType,selectedConverterType,request,response);
    }

    protected abstract T doBeforeBodyWrite(S body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response);
}
