package org.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-14 14:36:16
 * @since 1.0
 */
public class TypeJsonSerializer extends JsonSerializer<Type> {
    @Override
    public void serialize(Type value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}

