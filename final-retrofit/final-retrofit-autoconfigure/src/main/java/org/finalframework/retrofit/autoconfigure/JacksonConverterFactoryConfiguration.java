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

package org.finalframework.retrofit.autoconfigure;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 19:49:53
 * @since 1.0
 */
@Configuration
@SpringConfiguration
//@ConditionalOnBean(ObjectMapper.class)
//@ConditionalOnMissingBean(Converter.Factory.class)
public class JacksonConverterFactoryConfiguration {

    @Bean
    public JacksonConverterFactory jacksonConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }
}

