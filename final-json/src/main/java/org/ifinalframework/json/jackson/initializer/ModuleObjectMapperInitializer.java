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

package org.ifinalframework.json.jackson.initializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ServiceLoader;

import org.springframework.lang.NonNull;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.json.jackson.FinalJacksonModule;
import org.ifinalframework.json.jackson.ObjectMapperInitializer;
import org.ifinalframework.json.jackson.deserializer.LocalDateTimeExtDeserializer;
import org.ifinalframework.json.jackson.serializer.LocalDateSerializer;
import org.ifinalframework.json.jackson.serializer.LocalDateTimeSerializer;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

/**
 * ModuleObjectMapperInitializer.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(ObjectMapperInitializer.class)
public class ModuleObjectMapperInitializer implements ObjectMapperInitializer {

    @Override
    public void initialize(@NonNull final ObjectMapper objectMapper) {

        objectMapper.registerModule(new Jdk8Module());
        // java time module
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeExtDeserializer());
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer());
        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        javaTimeModule.addDeserializer(LocalTime.class,new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(javaTimeModule);

        objectMapper.registerModule(new FinalJacksonModule());

        ServiceLoader.load(Module.class, objectMapper.getClass().getClassLoader())
                .forEach(objectMapper::registerModule);

    }

}
