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

import org.finalframework.spring.annotation.factory.SpringResponseBodyAdvice;
import org.finalframework.spring.web.converter.Enums2EnumBeansConverter;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-24 23:31:45
 * @see Enums2EnumBeansConverter
 * @since 1.0
 */
//@SpringResponseBodyAdvice
//@Order(RestAdviceOrdered.DEFAULT_PRECEDENCE)
//@RestControllerAdvice
public class EnumsResponseBodyAdvice extends RestResponseBodyAdvice<Object> {

    private static final Enums2EnumBeansConverter enums2EnumBeansConverter = new Enums2EnumBeansConverter();

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return enums2EnumBeansConverter.matches(body) ? enums2EnumBeansConverter.convert(body) : body;
    }

}


