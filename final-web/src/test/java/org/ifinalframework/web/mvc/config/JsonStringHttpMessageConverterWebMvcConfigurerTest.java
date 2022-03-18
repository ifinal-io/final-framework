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

package org.ifinalframework.web.mvc.config;

import org.ifinalframework.http.converter.JsonStringHttpMessageConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.List;

/**
 * JsonStringHttpMessageConverterWebMvcConfigurerTest.
 *
 * @author ilikly
 * @version 1.2.4
 * @since 1.2.4
 */
@SpringBootApplication
@SpringBootTest
class JsonStringHttpMessageConverterWebMvcConfigurerTest {

    @Resource
    private RequestMappingHandlerAdapter adapter;

    @Test
    void extendMessageConverters() {
        final List<HttpMessageConverter<?>> converters = adapter.getMessageConverters();

        final boolean present = converters.stream().anyMatch(it -> it instanceof JsonStringHttpMessageConverter);

        Assertions.assertTrue(present);

    }
}
