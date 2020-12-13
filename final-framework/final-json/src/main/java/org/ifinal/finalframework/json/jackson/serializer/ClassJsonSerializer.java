package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public class ClassJsonSerializer extends JsonSerializer<Class> {

    @Override
    public void serialize(final Class value, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        gen.writeString(value.getName());

    }

}

