package org.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.finalframework.json.jackson.view.JsonViewValue;

import java.time.LocalDateTime;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-28 21:45:25
 * @since 1.0
 */
public class FinalJacksonModule extends SimpleModule {

    private final ObjectMapper objectMapper;

    public FinalJacksonModule(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.init();
    }

    private void init() {
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addSerializer(JsonViewValue.class, new JsonViewValueSerializer(objectMapper));
    }
}
