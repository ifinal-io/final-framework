package org.finalframework.json.jackson;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.finalframework.core.Factory;
import org.finalframework.json.jackson.modifier.BeanDatePropertySerializerModifier;
import org.finalframework.json.jackson.modifier.BeanEnumPropertySerializerModifier;
import org.finalframework.json.jackson.modifier.BeanLocalDatePropertySerializerModifier;
import org.finalframework.json.jackson.modifier.BeanLocalDateTimePropertySerializerModifier;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:42:29
 * @since 1.0
 */
public class ObjectMapperFactory implements Factory {
    private final ObjectMapper objectMapper;

    public ObjectMapperFactory() {
        this(new ObjectMapper());
    }

    public ObjectMapperFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new FinalJacksonModule(objectMapper));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanEnumPropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanLocalDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanLocalDateTimePropertySerializerModifier()));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    }

    public ObjectMapper create() {
        return this.objectMapper;
    }
}

