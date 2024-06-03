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

package org.ifinalframework.json.jackson.modifier;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.json.jackson.serializer.DateSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Bean对象{@link Date}类型属性序列化修改器。
 *
 * 序列化化时增加一个{@code xxxFormat}的属性，格式为{@code yyyy-MM-dd HH:mm:ss}。
 *
 * @author iimik
 * @version 1.0.0
 * @see BeanLocalDateTimePropertySerializerModifier
 * @since 1.0.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanDatePropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier {

    private static final DateSerializer dateSerializer = new DateSerializer("yyyy-MM-dd HH:mm:ss");

    @Override
    protected boolean support(final Class<?> clazz) {
        return Date.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config,
                                                           final BeanDescription beanDesc,
                                                           final BeanPropertyDefinition property, final BeanPropertyWriter writer) {

        final BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                dateSerializer, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        BeanSerializerModifierHelper.setPropertyName(bpw, bpw.getName() + "Format");
        return Collections.singleton(bpw);
    }

}

