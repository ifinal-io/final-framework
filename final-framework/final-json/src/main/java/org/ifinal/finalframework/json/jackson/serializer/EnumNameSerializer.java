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
public class EnumNameSerializer extends JsonSerializer<Enum<?>> {

    public static final EnumNameSerializer instance = new EnumNameSerializer();

    @Override
    public void serialize(final Enum<?> value, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        gen.writeString(value.name());
    }

}
