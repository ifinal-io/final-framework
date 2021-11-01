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

import org.ifinalframework.web.converter.EnumConverterFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;

/**
 * ConverterFactoryWebMvcConfigurerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ConverterFactoryWebMvcConfigurerTest {

    @Mock
    private ObjectProvider<List<ConverterFactory<?, ?>>> objectProvider;

    @Mock
    private FormatterRegistry registry;

    @Test
    void addFormatters() {

        List<ConverterFactory<?, ?>> converterFactories = Collections.singletonList(new EnumConverterFactory());
        when(objectProvider.getIfAvailable()).thenReturn(converterFactories);

        ConverterFactoryWebMvcConfigurer webMvcConfigurer = new ConverterFactoryWebMvcConfigurer(
            objectProvider);

        ArgumentCaptor<ConverterFactory> converterFactoryArgumentCaptor = ArgumentCaptor
            .forClass(ConverterFactory.class);

        webMvcConfigurer.addFormatters(registry);

        verify(registry, atLeastOnce()).addConverterFactory(converterFactoryArgumentCaptor.capture());

        List<ConverterFactory> values = converterFactoryArgumentCaptor.getAllValues();
        assertIterableEquals(converterFactories, values);

    }

}
