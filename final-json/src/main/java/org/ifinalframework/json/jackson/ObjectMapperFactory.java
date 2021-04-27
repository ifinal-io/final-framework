/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.json.jackson;

import java.util.ServiceLoader;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ObjectMapperFactory {

    private final ObjectMapper objectMapper;

    public ObjectMapperFactory() {
        this(new ObjectMapper());
    }

    public ObjectMapperFactory(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;

        ServiceLoader.load(ObjectMapperInitializer.class, getClass().getClassLoader())
            .forEach(it -> it.initialize(this.objectMapper));

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    public ObjectMapper create() {
        return this.objectMapper;
    }

}

