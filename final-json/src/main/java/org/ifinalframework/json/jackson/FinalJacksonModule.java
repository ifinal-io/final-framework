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

package org.ifinalframework.json.jackson;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.auto.service.annotation.AutoService;

import java.util.ServiceLoader;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@AutoService(Module.class)
public class FinalJacksonModule extends SimpleModule {

    @Override
    @SuppressWarnings("unchecked")
    public void setupModule(final SetupContext context) {

        ClassLoader classLoader = getClass().getClassLoader();

        ServiceLoader.load(JsonSerializer.class, classLoader).forEach(this::addSerializer);
        ServiceLoader.load(JsonDeserializer.class, classLoader).forEach(it -> addDeserializer(it.handledType(), it));

        ServiceLoader.load(Deserializers.class, classLoader).forEach(context::addDeserializers);
        ServiceLoader.load(Serializers.class, classLoader).forEach(context::addSerializers);
        ServiceLoader.load(BeanSerializerModifier.class, classLoader).forEach(context::addBeanSerializerModifier);
        ServiceLoader.load(BeanDeserializerModifier.class, classLoader).forEach(context::addBeanDeserializerModifier);
    }

}
