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

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.ifinalframework.http.converter.JsonStringHttpMessageConverter;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用{@link JsonStringHttpMessageConverter}替换{@link StringHttpMessageConverter}。
 *
 * <h6>注意：</h6>
 * <p>原生的{@link StringHttpMessageConverter}消息转换器，如果{@link ResponseBody}标记的方法的返回值是{@link  String}类型，会抛出{@link ClassCastException}。</p>
 *
 * @author iimik
 * @version 1.0.0
 * @see WebMvcConfigurer#configureMessageConverters(List)
 * @see WebMvcConfigurer#extendMessageConverters(List)
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnWebApplication
public class JsonStringHttpMessageConverterWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * Replace the {@link StringHttpMessageConverter} to {@link JsonStringHttpMessageConverter}.
     *
     * @param converters the build-in {@link HttpMessageConverter}s.
     */
    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {

        for (int i = 0; i < converters.size(); i++) {
            HttpMessageConverter<?> converter = converters.get(i);
            if (converter instanceof StringHttpMessageConverter) {
                // Use JsonStringHttpMessageConverter replace StringHttpMessageConverter.
                converter = new JsonStringHttpMessageConverter((StringHttpMessageConverter) converter);
                converters.set(i, converter);

                logger.info("replace StringHttpMessageConverter by JsonStringHttpMessageConverter: {}", converter);

            }
        }

    }

}
