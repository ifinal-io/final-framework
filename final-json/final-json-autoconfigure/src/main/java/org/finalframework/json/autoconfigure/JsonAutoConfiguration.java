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

package org.finalframework.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.finalframework.data.converter.EnumClassConverter;
import org.finalframework.data.converter.EnumConverter;
import org.finalframework.json.JsonRegistry;
import org.finalframework.json.JsonService;
import org.finalframework.json.jackson.JacksonJsonService;
import org.finalframework.json.jackson.ObjectMapperFactory;
import org.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:16:57
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@EnableConfigurationProperties(JsonProperties.class)
public class JsonAutoConfiguration {

    private final JsonProperties properties;
    private final EnumClassConverter enumClassConverter;
    @Resource
    private ObjectMapper objectMapper;

    public JsonAutoConfiguration(JsonProperties properties, ObjectProvider<EnumClassConverter> enumConverterProvider) {
        this.properties = properties;
        this.enumClassConverter = enumConverterProvider.getIfAvailable();
    }

    @PostConstruct
    public void initObjectMapper() {
        new ObjectMapperFactory(objectMapper, enumClassConverter);

        if (properties.getJsonService() != null) {
            try {
                JsonService jsonService = properties.getJsonService().newInstance();
                if (jsonService instanceof JacksonJsonService) {
                    ((JacksonJsonService) jsonService).setObjectMapper(objectMapper);
                }
                JsonRegistry.getInstance().register(jsonService);
            } catch (Exception e) {

            }

        }
    }


}
