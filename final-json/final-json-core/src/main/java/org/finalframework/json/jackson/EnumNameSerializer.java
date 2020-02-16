package org.finalframework.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.data.entity.enums.IEnum;

import java.io.IOException;

/**
 * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getDescription()}所描述的值。
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 16:19:06
 * @since 1.0
 */
public class EnumNameSerializer extends JsonSerializer<IEnum> {

    public static final EnumNameSerializer instance = new EnumNameSerializer();

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getDescription());
    }
}
