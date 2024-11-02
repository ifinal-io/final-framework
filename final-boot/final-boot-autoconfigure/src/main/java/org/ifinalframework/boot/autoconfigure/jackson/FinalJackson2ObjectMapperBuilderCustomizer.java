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

package org.ifinalframework.boot.autoconfigure.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import org.ifinalframework.json.Json;
import org.ifinalframework.json.jackson.ObjectMapperFactory;
import org.ifinalframework.json.jackson.deserializer.LocalDateTimeExtDeserializer;

import java.time.LocalDateTime;

/**
 * FinalJackson2ObjectMapperBuilderCustomizer.
 *
 * @author iimik
 * @version 1.3.3
 * @since 1.3.3
 */
@Component
@ConditionalOnClass(Json.class)
public class FinalJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeExtDeserializer());
        jacksonObjectMapperBuilder.postConfigurer(objectMapper -> {
            ObjectMapper mapper = new ObjectMapperFactory(objectMapper).create();
            //            JsonRegistry.getInstance().register(new JacksonJsonService(mapper));
        });
    }
}


