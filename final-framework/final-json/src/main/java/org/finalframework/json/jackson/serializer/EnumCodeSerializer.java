package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.annotation.IEnum;

import java.io.IOException;

/**
 * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getCode()}所描述的值。
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 16:38:26
 * @since 1.0
 */
public class EnumCodeSerializer extends JsonSerializer<IEnum> {
    public static final EnumCodeSerializer instance = new EnumCodeSerializer();

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getCode());
    }
}
