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

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.ifinalframework.util.Asserts;

import java.util.List;
import java.util.ServiceLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * An {@link WebMvcConfigurer} auto-detects {@link ConverterFactory} beans to {@link
 * WebMvcConfigurer#addFormatters(FormatterRegistry)}. Also support SPI from {@link ServiceLoader}.
 *
 * @author ilikly
 * @version 1.0.0
 * @see ConverterFactory
 * @see FormatterRegistry
 * @see ServiceLoader
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ConverterFactoryWebMvcConfigurer implements WebMvcConfigurer {

    private final List<ConverterFactory<?, ?>> converterFactories;

    public ConverterFactoryWebMvcConfigurer(
            final ObjectProvider<List<ConverterFactory<?, ?>>> converterFactoriesProvider) {
        this.converterFactories = converterFactoriesProvider.getIfAvailable();
    }

    @Override
    public void addFormatters(final @NonNull FormatterRegistry registry) {

        logger.info("start register converterFactories ...");
        if (Asserts.nonEmpty(converterFactories)) {
            converterFactories.forEach(item -> this.addConverterFactory(registry, item));
        }
        ServiceLoader.load(ConverterFactory.class).forEach(item -> this.addConverterFactory(registry, item));
        logger.info("finish register converterFactories ...");
    }

    private void addConverterFactory(final FormatterRegistry registry, final ConverterFactory<?, ?> factory) {
        if (logger.isInfoEnabled()) {
            logger.info("addConverterFactory: {}", factory.getClass().getName());
        }
        registry.addConverterFactory(factory);
    }

}
