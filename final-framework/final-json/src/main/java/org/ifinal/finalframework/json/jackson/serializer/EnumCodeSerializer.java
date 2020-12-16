package org.ifinal.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.ifinal.finalframework.origin.IEnum;

/**
 * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getCode()}所描述的值。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("rawtypes")
public class EnumCodeSerializer extends JsonSerializer<IEnum> {

    public static final EnumCodeSerializer instance = new EnumCodeSerializer();

    @Override
    public void serialize(final IEnum value, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException {

        gen.writeObject(value.getCode());
    }

}
