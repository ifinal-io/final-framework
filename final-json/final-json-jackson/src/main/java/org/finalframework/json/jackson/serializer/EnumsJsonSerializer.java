package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.entity.enums.IEnum;

import java.io.IOException;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 16:43:22
 * @since 1.0
 */
public class EnumsJsonSerializer extends JsonSerializer<Collection<IEnum>> {

    public static final EnumsJsonSerializer instance = new EnumsJsonSerializer();


    @Override
    public void serialize(Collection<IEnum> enums, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();

        for (IEnum item : enums) {
            gen.writeStartObject();
            gen.writeFieldName("code");
            gen.writeObject(item.getCode());
            gen.writeFieldName("name");
            gen.writeObject(item.getDescription());
            gen.writeEndObject();
        }

        gen.writeEndArray();
    }
}
