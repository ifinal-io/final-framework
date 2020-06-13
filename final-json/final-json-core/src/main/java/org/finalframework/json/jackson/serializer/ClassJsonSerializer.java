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
import org.finalframework.data.converter.EnumClassConverter;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-08 15:37:26
 * @since 1.0
 */
public class ClassJsonSerializer extends JsonSerializer<Class> {

    private final EnumClassConverter enumClassConverter;

    public ClassJsonSerializer(EnumClassConverter enumClassConverter) {
        this.enumClassConverter = enumClassConverter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(Class value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.isEnum() && enumClassConverter != null) {
            gen.writeObject(enumClassConverter.convert((Class<Enum<?>>) value));
        } else {
            gen.writeString(value.getCanonicalName());
        }

    }
}

