package org.ifinal.finalframework.data.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

