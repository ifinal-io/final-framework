

package org.finalframework.json.jackson.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.annotation.Enums;
import org.finalframework.annotation.data.EnumValue;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-25 10:33:52
 * @since 1.0
 */
public class EnumValueNameSerializer extends JsonSerializer<Object> {

    private final EnumValue enumValue;

    public EnumValueNameSerializer(EnumValue enumValue) {
        this.enumValue = enumValue;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        Enum<?> anEnum = Enums.findEnum(enumValue, value);
        gen.writeString(anEnum.name());
    }
}

