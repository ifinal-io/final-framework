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

package org.ifinalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.json.jackson.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;

/**
 * Modify bean {@link LocalDateTime} property serializer append a new {@link BeanPropertyWriter} named {@code xxxFormat}.
 *
 * @author likly
 * @version 1.0.0
 * @see LocalDateTime
 * @since 1.0.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanLocalDateTimePropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final LocalDateTimeSerializer SERIALIZER = new LocalDateTimeSerializer(FORMATTER);

    @Override
    protected boolean support(final Class<?> clazz) {
        return LocalDateTime.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config,
                                                           final BeanDescription beanDesc,
                                                           final BeanPropertyDefinition property, final BeanPropertyWriter writer) {
        //创建一个新的属性来描述增加的"xxxName"，并使用 EnumNameSerializer 来序列化该属性
        final BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                SERIALIZER, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());
        String name = bpw.getName();

        setNameValue(bpw, name + "Format");


        return Collections.singletonList(bpw);
    }

}

