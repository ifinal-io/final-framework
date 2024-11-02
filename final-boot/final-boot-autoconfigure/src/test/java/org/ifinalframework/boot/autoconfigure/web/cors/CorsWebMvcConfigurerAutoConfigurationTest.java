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

package org.ifinalframework.boot.autoconfigure.web.cors;

import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * CorsWebMvcConfigurerAutoConfigurationTest.
 *
 * @author iimik
 * @version 1.4.2
 * @since 1.4.2
 */
@ExtendWith(MockitoExtension.class)
class CorsWebMvcConfigurerAutoConfigurationTest {

    @Test
    void addCorsMappings() {
        CorsProperties corsProperties = new CorsProperties();
        corsProperties.setMapping("/**");
        corsProperties.setAllowedHeaders(new String[]{"header"});
        corsProperties.setAllowedMethods(new String[]{"GET"});
        corsProperties.setAllowedOrigins(new String[]{"Origin"});
        CorsWebMvcConfigurerAutoConfiguration configuration = new CorsWebMvcConfigurerAutoConfiguration(corsProperties);
        CorsRegistry registry = Mockito.mock(CorsRegistry.class);

        CorsRegistration corsRegistration = Mockito.mock(CorsRegistration.class);
        Mockito.when(registry.addMapping(Mockito.anyString())).thenReturn(corsRegistration);

        configuration.addCorsMappings(registry);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(corsRegistration).allowedHeaders(argumentCaptor.capture());
        Assertions.assertEquals(argumentCaptor.getValue(), corsProperties.getAllowedHeaders()[0]);

        ArgumentCaptor<String> argumentCaptorMethod = ArgumentCaptor.forClass(String.class);
        Mockito.verify(corsRegistration).allowedMethods(argumentCaptorMethod.capture());
        Assertions.assertEquals(argumentCaptorMethod.getValue(), corsProperties.getAllowedMethods()[0]);

        ArgumentCaptor<String> argumentCaptorOrigin = ArgumentCaptor.forClass(String.class);
        Mockito.verify(corsRegistration).allowedOrigins(argumentCaptorOrigin.capture());
        Assertions.assertEquals(argumentCaptorOrigin.getValue(), corsProperties.getAllowedOrigins()[0]);

    }
}