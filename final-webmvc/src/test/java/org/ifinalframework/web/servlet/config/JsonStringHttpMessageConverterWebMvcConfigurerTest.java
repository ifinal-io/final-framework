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

package org.ifinalframework.web.servlet.config;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import org.ifinalframework.http.converter.JsonStringHttpMessageConverter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonStringHttpMessageConverterWebMvcConfigurerTest.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
class JsonStringHttpMessageConverterWebMvcConfigurerTest {

    @Test
    void extendMessageConverters() {
        JsonStringHttpMessageConverterWebMvcConfigurer configurer = new JsonStringHttpMessageConverterWebMvcConfigurer();
        List<HttpMessageConverter<?>> converters = Arrays.asList(new StringHttpMessageConverter());
        configurer.extendMessageConverters(converters);
        assertInstanceOf(JsonStringHttpMessageConverter.class, converters.get(0));
    }
}