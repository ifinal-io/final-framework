package org.ifinal.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.ifinal.finalframework.annotation.core.Enums;
import org.ifinal.finalframework.annotation.data.EnumValue;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumValueNameSerializer extends JsonSerializer<Object> {

    private final EnumValue enumValue;

    public EnumValueNameSerializer(final EnumValue enumValue) {

        this.enumValue = enumValue;
    }

    @Override
    public void serialize(final Object value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }
        Enum<?> anEnum = Enums.findEnum(enumValue, value);
        gen.writeString(anEnum.name());
    }
}

