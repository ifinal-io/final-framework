/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.http.converter;

import org.ifinalframework.json.Json;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 包装 {@link StringHttpMessageConverter} 以解决使用 {@link ResponseBodyAdvice} 方式, 处理{@link HandlerMethod} 返回类型与声明类型不一致时，导致抛出
 * {@link ClassCastException}。
 *
 * @author likly
 * @version 1.0.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#extendMessageConverters(List)
 * @since 1.0.0
 */
public class JsonStringHttpMessageConverter implements HttpMessageConverter<Object> {

    private final StringHttpMessageConverter converter;

    public JsonStringHttpMessageConverter(StringHttpMessageConverter converter) {
        this.converter = Objects.requireNonNull(converter, "StringHttpMessageConverter can not be null!");
    }

    @Override
    public boolean canRead(final @NonNull Class<?> clazz, final MediaType mediaType) {

        return converter.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(final @NonNull Class<?> clazz, final MediaType mediaType) {

        return converter.canWrite(String.class, mediaType);
    }

    @NonNull
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return converter.getSupportedMediaTypes();
    }

    @NonNull
    @Override
    public Object read(final @NonNull Class<?> clazz, final @NonNull HttpInputMessage inputMessage)
            throws IOException {

        return converter.read(String.class, inputMessage);
    }

    @Override
    public void write(final @NonNull Object o, final MediaType contentType,
                      final @NonNull HttpOutputMessage outputMessage) throws IOException {

        converter.write(Json.toJson(o), contentType, outputMessage);
    }

}
