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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.ifinalframework.auto.service.annotation.AutoService;
import org.ifinalframework.data.annotation.EnumValue;
import org.ifinalframework.util.Enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * EnumValueBeanPropertySerializerModifier.
 *
 * @author ilikly
 * @version 1.4.2
 * @since 1.4.2
 */
@AutoService(BeanSerializerModifier.class)
public class EnumValueBeanPropertySerializerModifier extends AbsBeanPropertySerializerModifier {
    @Override
    public Collection<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, BeanPropertyDefinition property, BeanPropertyWriter writer) {
        EnumValue enumValue = writer.findAnnotation(EnumValue.class);

        final BeanPropertyWriter bpw = new BeanPropertyWriter(property,
                writer.getMember(), beanDesc.getClassAnnotations(), property.getPrimaryType(),
                new EnumValueJsonSerializer(enumValue), writer.getTypeSerializer(), writer.getSerializationType(),
                writer.willSuppressNulls(), null, property.findViews());

        setNameValue(bpw, bpw.getName() + "Name");
        return Collections.singleton(bpw);
    }

    @Override
    public boolean test(BeanPropertyDefinition beanPropertyDefinition, BeanPropertyWriter beanPropertyWriter) {
        return beanPropertyWriter.findAnnotation(EnumValue.class) != null;
    }

    private static class EnumValueJsonSerializer extends StdSerializer<Object> {
        private final EnumValue enumValue;

        public EnumValueJsonSerializer(EnumValue enumValue) {
            super(Object.class);
            this.enumValue = enumValue;
        }


        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            Enum<?> target = Enums.findEnum(enumValue.value(), enumValue.creator(), enumValue.valueType(), value);
            if (Objects.isNull(target)) {
                gen.writeNull();
            } else {
                String desc = Enums.findDesc(target, enumValue.desc());
                gen.writeString(desc);
            }
        }
    }
}


