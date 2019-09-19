package org.finalframework.data.result.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.core.Assert;
import org.finalframework.data.entity.enums.IEnum;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-26 18:15:37
 * @since 1.0
 */
public class EnumsDataSerializer extends DataSerializer<Object> {
    @Override
    boolean supports(Object value) {

        if (value.getClass().isArray()) {
            if (Assert.isEmpty(value)) return false;
            return ((Object[]) value)[0] instanceof IEnum;
        }

        if (value instanceof List) {
            if (Assert.isEmpty(value)) return false;
            return ((List) value).get(0) instanceof IEnum;
        }

        if (value instanceof Set) {
            if (Assert.isEmpty(value)) return false;
            return ((Set) value).toArray()[0] instanceof IEnum;
        }

        return false;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        if (value instanceof Collection) {
            Collection<IEnum> enums = (Collection<IEnum>) value;
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
        } else if (value.getClass().isArray()) {
            IEnum[] enums = (IEnum[]) value;
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
}
