package org.ifinal.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.IEnum;
import org.ifinal.finalframework.context.converter.EnumClassConverter;
import org.ifinal.finalframework.json.jackson.deserializer.LocalDateTimeDeserializer;
import org.ifinal.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.ifinal.finalframework.json.jackson.serializer.EnumCodeSerializer;
import org.ifinal.finalframework.json.jackson.serializer.JsonViewValueSerializer;
import org.ifinal.finalframework.json.jackson.serializer.LocalDateTimeSerializer;
import org.ifinal.finalframework.json.jackson.view.JsonViewValue;
import org.springframework.lang.NonNull;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class FinalJacksonModule extends SimpleModule {

    private final ObjectMapper objectMapper;
    private final EnumClassConverter enumClassConverter;

    public FinalJacksonModule(ObjectMapper objectMapper) {
        this(objectMapper, new EnumClassConverter(null));
    }

    public FinalJacksonModule(ObjectMapper objectMapper, EnumClassConverter enumClassConverter) {
        this.objectMapper = objectMapper;
        this.enumClassConverter = enumClassConverter;
        this.init();
    }

    private void init() {

        addSerializer(IEnum.class, EnumCodeSerializer.instance);

        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addSerializer(JsonViewValue.class, new JsonViewValueSerializer(objectMapper));
        addSerializer(Class.class, new ClassJsonSerializer(enumClassConverter));


    }

    @SuppressWarnings("unchecked")
    private <T> T newInstance(@NonNull Class<? extends T> clazz) {
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getConstructor(ObjectMapper.class);
            return (T) constructor.newInstance(this.objectMapper);
        } catch (Exception e) {
            try {
                return clazz.newInstance();
            } catch (Exception error) {
                throw new RuntimeException(error);
            }
        }
    }
}
