package org.ifinal.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.json.jackson.deserializer.IEnumDeserializers;
import org.ifinal.finalframework.json.jackson.deserializer.LocalDateTimeDeserializer;
import org.ifinal.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.ifinal.finalframework.json.jackson.serializer.LocalDateTimeSerializer;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class FinalJacksonModule extends SimpleModule {

    private final ObjectMapper objectMapper;

    public FinalJacksonModule(final ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
        init();
    }

    private void init() {

        setDeserializers(new IEnumDeserializers());

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addSerializer(Class.class, new ClassJsonSerializer());

    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(final @NonNull Class<? extends T> clazz) {

        final Constructor<?> constructor;
        try {
            constructor = clazz.getConstructor(ObjectMapper.class);
            return (T) constructor.newInstance(this.objectMapper);
        } catch (Exception e) {
            try {
                return clazz.getConstructor().newInstance();
            } catch (Exception error) {
                throw new IllegalArgumentException(error);
            }
        }
    }

}
