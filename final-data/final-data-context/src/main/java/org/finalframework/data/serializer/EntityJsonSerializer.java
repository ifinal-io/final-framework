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

package org.finalframework.data.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.mapping.Property;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 17:59:44
 * @since 1.0
 */
public class EntityJsonSerializer extends JsonSerializer<Entity<?>> {
    @Override
    public void serialize(Entity<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();

        gen.writeStringField("@class", value.getType().getCanonicalName());
        gen.writeStringField("name", value.getType().getSimpleName());

        gen.writeFieldName("properties");

        gen.writeStartArray();


        for (Property property : value) {
            gen.writeObject(property);
        }

        gen.writeEndArray();

        gen.writeEndObject();

    }
}

