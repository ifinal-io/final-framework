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

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.core.IEnum;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

/**
 * BeanEnumPropertySerializerModifier.
 *
 * <p>Feature:</p>
 * <ul>
 *     <li>the property serialize the value of {@link IEnum#getCode()}.</li>
 *     <li>add property name serialize the value of {@link Enum#name()}.</li>
 *     <li>add property desc serialize the value of {@link IEnum#getDesc()}.</li>
 * </ul>
 *
 * <p>Java Entity Example:</p>
 * <pre class="code">
 *     &#64;Data
 *     static class EnumBean {
 *          private YN yn = YN.YES;
 *     }
 * </pre>
 * <p>Json Example:</p>
 * <pre class="code">
 *      {
 *          "yn": 1,
 *          "ynName": "YES",
 *          "ynDesc": "有效"
 *      }
 * </pre>
 *
 * @author ilikly
 * @version 1.0.0
 * @see EnumCodeSerializer
 * @see EnumNameSerializer
 * @see EnumDescSerializer
 * @since 1.0.0
 */
@AutoService(BeanSerializerModifier.class)
public class BeanEnumPropertySerializerModifier extends AbsSimpleBeanPropertySerializerModifier {

    private static final String ENUM_NAME_PROPERTY_SUFFIX = "Name";

    private static final String ENUM_DESC_PROPERTY_SUFFIX = "Desc";

    @Override
    public JsonSerializer<?> modifyEnumSerializer(final SerializationConfig config, final JavaType valueType,
                                                  final BeanDescription beanDesc, final JsonSerializer<?> serializer) {

        if (IEnum.class.isAssignableFrom(valueType.getRawClass())) {
            return EnumCodeSerializer.instance;
        }
        return super.modifyEnumSerializer(config, valueType, beanDesc, serializer);
    }

    @Override
    protected boolean support(final Class<?> clazz) {
        return IEnum.class.isAssignableFrom(clazz);
    }

    @Override
    public Collection<BeanPropertyWriter> changeProperties(final SerializationConfig config,
                                                           final BeanDescription beanDesc,
                                                           final BeanPropertyDefinition property, final BeanPropertyWriter writer) {
        final BeanPropertyWriter enumNamePropertyWriter = buildEnumNamePropertyWriter(beanDesc, property, writer);
        final BeanPropertyWriter enumDescPropertyWriter = buildEnumDescPropertyWriter(beanDesc, property, writer);
        return Arrays.asList(enumNamePropertyWriter, enumDescPropertyWriter);
    }

    private BeanPropertyWriter buildEnumNamePropertyWriter(final BeanDescription beanDesc,
                                                           final BeanPropertyDefinition property,
                                                           final BeanPropertyWriter writer) {

        final BeanPropertyWriter enumNamePropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumNameSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        setNameValue(enumNamePropertyWriter, enumNamePropertyWriter.getName() + ENUM_NAME_PROPERTY_SUFFIX);
        return enumNamePropertyWriter;
    }

    private BeanPropertyWriter buildEnumDescPropertyWriter(final BeanDescription beanDesc,
                                                           final BeanPropertyDefinition property,
                                                           final BeanPropertyWriter writer) {

        final BeanPropertyWriter enumDescriptionPropertyWriter = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                EnumDescSerializer.instance, writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());
        setNameValue(enumDescriptionPropertyWriter,
                enumDescriptionPropertyWriter.getName() + ENUM_DESC_PROPERTY_SUFFIX);
        return enumDescriptionPropertyWriter;
    }

    /**
     * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getCode()}所描述的值。
     *
     * @author ilikly
     * @version 1.0.0
     * @since 1.0.0
     */
    @SuppressWarnings("rawtypes")
    static class EnumCodeSerializer extends JsonSerializer<IEnum> {

        public static final EnumCodeSerializer instance = new EnumCodeSerializer();

        @Override
        public void serialize(final IEnum value, final JsonGenerator gen, final SerializerProvider serializers)
                throws IOException {

            final Object code = value.getCode();
            if(code instanceof String){
                gen.writeString((String) code);
            }else {
                gen.writeObject(code);
            }
        }

    }

    /**
     * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getDesc()}所描述的值。
     *
     * @author ilikly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumDescSerializer extends JsonSerializer<IEnum<?>> {

        public static final EnumDescSerializer instance = new EnumDescSerializer();

        @Override
        public void serialize(final IEnum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.getDesc());
        }

    }

    /**
     * @author ilikly
     * @version 1.0.0
     * @since 1.0.0
     */
    private static class EnumNameSerializer extends JsonSerializer<Enum<?>> {

        public static final EnumNameSerializer instance = new EnumNameSerializer();

        @Override
        public void serialize(final Enum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
                throws IOException {

            gen.writeString(value.name());
        }

    }

}
