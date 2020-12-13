package org.ifinal.finalframework.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.core.IEnum;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumDeserializer<T extends IEnum<?>> extends JsonDeserializer<T> {

    private final Class<T> enumType;

    private final Map<String, T> cache;

    public EnumDeserializer(final Class<T> enumType) {

        Objects.requireNonNull(enumType, "the enumType must be not null!");
        this.enumType = enumType;
        this.cache = Arrays.stream(enumType.getEnumConstants()).collect(Collectors.toMap(it -> it.getCode().toString(), Function.identity()));
    }

    @Override
    public T deserialize(final JsonParser p, final DeserializationContext context) throws IOException {

        final String code = p.getValueAsString();
        return cache.get(code);
    }

    public String toString() {
        return "EnumDeserializer{" + "enumType=" + enumType + '}';
    }

}
