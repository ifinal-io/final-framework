

package org.finalframework.json.jackson;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.finalframework.context.converter.EnumClassConverter;
import org.finalframework.core.Factory;
import org.finalframework.json.jackson.modifier.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:42:29
 * @since 1.0
 */
public class ObjectMapperFactory implements Factory {
    private final ObjectMapper objectMapper;

    public ObjectMapperFactory() {
        this(new ObjectMapper(), new EnumClassConverter(null));
    }

    public ObjectMapperFactory(ObjectMapper objectMapper, EnumClassConverter enumClassConverter) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new FinalJacksonModule(objectMapper, enumClassConverter));
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new BeanEnumPropertySerializerModifier()));
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new BeanEnumValuePropertySerializerModifier()));
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new BeanDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(
                objectMapper.getSerializerFactory().withSerializerModifier(new BeanLocalDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(new BeanLocalDateTimePropertySerializerModifier()));


        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    public ObjectMapper create() {
        return this.objectMapper;
    }
}

