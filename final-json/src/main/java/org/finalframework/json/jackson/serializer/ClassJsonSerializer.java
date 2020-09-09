

package org.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.context.converter.EnumClassConverter;

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

