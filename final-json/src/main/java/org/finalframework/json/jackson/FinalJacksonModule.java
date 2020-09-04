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

package org.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.finalframework.context.converter.EnumClassConverter;
import org.finalframework.json.jackson.deserializer.LocalDateTimeDeserializer;
import org.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.finalframework.json.jackson.serializer.JsonViewValueSerializer;
import org.finalframework.json.jackson.serializer.LocalDateTimeSerializer;
import org.finalframework.json.jackson.view.JsonViewValue;

import java.time.LocalDateTime;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 21:45:25
 * @since 1.0
 */
public class FinalJacksonModule extends SimpleModule {

    private final ObjectMapper objectMapper;
    private final EnumClassConverter enumClassConverter;

    public FinalJacksonModule(ObjectMapper objectMapper) {
        this(objectMapper, new EnumClassConverter(null));
    }

    public FinalJacksonModule(ObjectMapper objectMapper, EnumClassConverter enumClassConverter) {
        this.objectMapper = objectMapper;
        this.enumClassConverter = enumClassConverter;
        this.init();
    }

    private void init() {
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addSerializer(JsonViewValue.class, new JsonViewValueSerializer(objectMapper));
        addSerializer(Class.class, new ClassJsonSerializer(enumClassConverter));
    }
}
