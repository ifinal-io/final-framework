package org.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDateTime;

import org.finalframework.data.converter.EnumClassConverter;
import org.finalframework.data.query.Query;
import org.finalframework.json.jackson.deserializer.LocalDateTimeDeserializer;
import org.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.finalframework.json.jackson.serializer.JsonViewValueSerializer;
import org.finalframework.json.jackson.serializer.LocalDateTimeSerializer;
import org.finalframework.json.jackson.serializer.query.QuerySerializer;
import org.finalframework.json.jackson.view.JsonViewValue;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 21:45:25
 * @since 1.0
 */
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
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());

        addSerializer(JsonViewValue.class, new JsonViewValueSerializer(objectMapper));
        addSerializer(Query.class, new QuerySerializer());

        addSerializer(Class.class, new ClassJsonSerializer(enumClassConverter));
    }
}
