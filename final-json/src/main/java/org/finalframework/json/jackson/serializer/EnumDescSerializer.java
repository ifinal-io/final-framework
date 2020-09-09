

package org.finalframework.json.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.finalframework.annotation.IEnum;
import org.finalframework.context.util.Messages;

import java.io.IOException;
import java.util.Locale;

/**
 * 枚举{@link IEnum}码序列化器，将枚举序列化为其{@link IEnum#getDesc()}所描述的值。
 *
 * @author likly
 * @version 1.0
 * @date 2019-08-26 16:19:06
 * @since 1.0
 */
public class EnumDescSerializer extends JsonSerializer<IEnum> {

    public static final EnumDescSerializer instance = new EnumDescSerializer();

    @Override
    public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        final String code = String.format("%s.%s", value.getClass().getCanonicalName(), ((Enum<?>) value).name().toLowerCase(Locale.ENGLISH));
        gen.writeString(Messages.getMessage(code, value.getDesc()));
    }
}
