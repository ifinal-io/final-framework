

package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 16:19:06
 * @since 1.0
 */
public class EnumNameSerializer extends JsonSerializer<Enum> {

    public static final EnumNameSerializer instance = new EnumNameSerializer();

    @Override
    public void serialize(Enum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.name());
    }
}
