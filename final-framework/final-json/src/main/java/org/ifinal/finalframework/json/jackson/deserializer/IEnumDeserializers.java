package org.ifinal.finalframework.json.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.core.IEnum;

/**
 * IEnumSerializers.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class IEnumDeserializers extends SimpleDeserializers {

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findEnumDeserializer(final Class<?> type, final DeserializationConfig config, final BeanDescription beanDesc)
        throws JsonMappingException {

        if (IEnum.class.isAssignableFrom(type)) {
            return new EnumDeserializer<>((Class<? extends IEnum<?>>) type);
        }

        return super.findEnumDeserializer(type, config, beanDesc);
    }

    /**
     * @author likly
     * @version 1.0.0
     * @since 1.0.0
     */
    public static class EnumDeserializer<T extends IEnum<?>> extends JsonDeserializer<T> {

        private final Class<T> enumType;

        private final Map<String, T> cache;

        public EnumDeserializer(final Class<T> enumType) {

            Objects.requireNonNull(enumType, "the enumType must be not null!");
            this.enumType = enumType;
            this.cache = Arrays.stream(enumType.getEnumConstants())
                .collect(Collectors.toMap(it -> it.getCode().toString(), Function.identity()));
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

}
