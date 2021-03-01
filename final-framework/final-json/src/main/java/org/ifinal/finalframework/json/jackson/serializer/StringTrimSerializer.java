package org.ifinal.finalframework.json.jackson.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * StringTrimSerializer.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class StringTrimSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(final String value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeString(value.trim());
    }

}
