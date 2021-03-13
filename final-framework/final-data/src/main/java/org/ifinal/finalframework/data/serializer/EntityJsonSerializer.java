package org.ifinal.finalframework.data.serializer;

import org.ifinal.finalframework.data.mapping.Entity;
import org.ifinal.finalframework.data.mapping.Property;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EntityJsonSerializer extends JsonSerializer<Entity<?>> {

    @Override
    public void serialize(final Entity<?> value, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

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

