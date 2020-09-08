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

package org.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.annotation.Enums;
import org.finalframework.annotation.data.EnumValue;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:33:52
 * @since 1.0
 */
public class EnumValueDescSerializer extends JsonSerializer<Object> {

    private final EnumValue enumValue;

    public EnumValueDescSerializer(EnumValue enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Enum anEnum = Enums.findEnum(enumValue, value);
        Field field = ReflectionUtils.findField(enumValue.value(), enumValue.desc());
        field.setAccessible(true);
        Object description = ReflectionUtils.getField(field, anEnum);
        gen.writeString(description.toString());
    }
}

