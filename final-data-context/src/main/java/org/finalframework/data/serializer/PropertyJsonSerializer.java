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
import org.finalframework.data.annotation.Json;
import org.finalframework.data.mapping.Property;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 18:03:19
 * @since 1.0
 */
public class PropertyJsonSerializer extends JsonSerializer<Property> {
    @Override
    public void serialize(Property property, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeStringField("name", property.getName());
        gen.writeStringField("column", property.getColumn());
        gen.writeStringField("type", property.getType().getCanonicalName());
//        gen.writeStringField("javaType", property.getJavaType().getCanonicalName());

        if (property.isMap()) {
            gen.writeStringField("keyType", property.getComponentType().getCanonicalName());
            gen.writeStringField("valueType", property.getMapValueType().getCanonicalName());
        } else if (property.isCollectionLike()) {
            gen.writeStringField("elementType", property.getComponentType().getCanonicalName());
        }

        gen.writeFieldName("properties");
        gen.writeStartObject();

        gen.writeBooleanField("isIdProperty", property.isIdProperty());
        gen.writeBooleanField("isEnum", property.isEnum());
        gen.writeBooleanField("isReference", property.isReference());
        gen.writeBooleanField("isJson", property.hasAnnotation(Json.class));
        gen.writeBooleanField("isTransient", property.isTransient());
        gen.writeBooleanField("isVirtual", property.isVirtual());
        gen.writeBooleanField("isReadOnly", property.isReadOnly());
        gen.writeBooleanField("isWriteOnly", property.isWriteOnly());

        gen.writeEndObject();


        gen.writeEndObject();
    }
}

