package org.ifinal.finalframework.json.jackson;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.ifinal.finalframework.util.Asserts;

import java.util.ServiceLoader;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ObjectMapperFactory {
    private final ObjectMapper objectMapper;

    public ObjectMapperFactory() {
        this(new ObjectMapper());
    }

    public ObjectMapperFactory(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new FinalJacksonModule(objectMapper));

        ServiceLoader<BeanSerializerModifier> beanSerializerModifiers = ServiceLoader.load(BeanSerializerModifier.class, getClass().getClassLoader());

        if (Asserts.nonEmpty(beanSerializerModifiers)) {
            beanSerializerModifiers.forEach(beanSerializerModifier -> objectMapper.setSerializerFactory(
                    objectMapper.getSerializerFactory()
                            .withSerializerModifier(beanSerializerModifier)
            ));

        }


        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
    }

    public ObjectMapper create() {
        return this.objectMapper;
    }
}

