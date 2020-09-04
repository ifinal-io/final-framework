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

package org.finalframework.json.jackson.modifier;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import org.finalframework.data.annotation.IEnum;
import org.finalframework.json.jackson.serializer.EnumCodeSerializer;
import org.finalframework.json.jackson.serializer.EnumDescSerializer;
import org.finalframework.json.jackson.serializer.EnumNameSerializer;
import org.finalframework.json.jackson.serializer.EnumSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

/**
 * 对JavaBean的枚举型属性（实现了{@link IEnum}接口）进行序列化修改。
 * <p/>
 * 1. 将该枚举属性序列化为{@link IEnum#getCode()}所对应的值。
 * 2. 增加"xxxName"属性，其值为{@link IEnum#getDesc()}所对应的值。
 * <p/>
 * <a href="https://final.ilikly.com/json/jackson/serializer/bean-enum-property-serializer-modifier">BeanEnumPropertySerializerModifier</a>
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 14:27:05
 * @see EnumCodeSerializer
 * @see EnumNameSerializer
 * @see EnumDescSerializer
 * @since 1.0
 */
public class BeanEnumPropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier<IEnum> {

    private static final Logger logger = LoggerFactory.getLogger(BeanEnumPropertySerializerModifier.class);

    private static final String ENUM_NAME_PROPERTY_SUFFIX = "Name";
    private static final String ENUM_DESC_PROPERTY_SUFFIX = "Desc";
    private static final String ENUM_DESCRIPTION_PROPERTY_SUFFIX = "Description";

    @Override
    public JsonSerializer<?> modifyEnumSerializer(SerializationConfig config, JavaType valueType, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        if (IEnum.class.isAssignableFrom(valueType.getRawClass())) {
            JsonFormat jsonFormat = beanDesc.getClassAnnotations().get(JsonFormat.class);
            if (jsonFormat != null && JsonFormat.Shape.OBJECT == jsonFormat.shape()) {
                return EnumSerializer.instance;
            }
            return EnumCodeSerializer.instance;
        }
        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }

    @Override
    protected boolean support(Class<?> clazz) {
        return IEnum.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumNamePropertyWriter = buildEnumNamePropertyWriter(beanDesc, property, writer);
        BeanPropertyWriter enumDescPropertyWriter = buildEnumDescPropertyWriter(beanDesc, property, writer);
        BeanPropertyWriter enumDescriptionPropertyWriter = buildEnumDescriptionPropertyWriter(beanDesc, property, writer);
        return Arrays.asList(enumNamePropertyWriter, enumDescPropertyWriter, enumDescriptionPropertyWriter);
    }

    private BeanPropertyWriter buildEnumNamePropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumNamePropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumNameSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumNamePropertyWriter, new SerializedString(enumNamePropertyWriter.getName() + ENUM_NAME_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumNamePropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescriptionPropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumDescSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumDescriptionPropertyWriter, new SerializedString(enumDescriptionPropertyWriter.getName() + ENUM_DESCRIPTION_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumDescriptionPropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescPropertyWriter(BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumDescSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        try {
            Field name = BeanPropertyWriter.class.getDeclaredField("_name");
            name.setAccessible(true);
            name.set(enumDescriptionPropertyWriter, new SerializedString(enumDescriptionPropertyWriter.getName() + ENUM_DESC_PROPERTY_SUFFIX));
        } catch (Exception e) {
            logger.error("", e);
        }
        return enumDescriptionPropertyWriter;
    }
}
