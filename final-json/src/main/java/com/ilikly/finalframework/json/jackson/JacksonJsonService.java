package com.ilikly.finalframework.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ilikly.finalframework.json.JsonException;
import com.ilikly.finalframework.json.JsonRegistry;
import com.ilikly.finalframework.json.JsonService;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:39
 * @since 1.0
 */
@ConditionalOnClass(ObjectMapper.class)
public class JacksonJsonService implements JsonService {

    @Resource
    @Setter
    private ObjectMapper objectMapper;

    @PostConstruct
    public void initObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new SimpleModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
    }


    @PostConstruct
    private void init() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        JsonRegistry.getInstance().registerJsonService(this);
    }

    @Override
    public String toJson(Object object) throws Throwable {
            return objectMapper.writeValueAsString(object);
    }

    @Override
    public <T> T parse(String json, Class<T> classOfT) {
        try {
            return objectMapper.readValue(json, classOfT);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parse(String json, Type typeOfT) throws Throwable {
        return objectMapper.readValue(json, objectMapper.getTypeFactory().constructType(typeOfT));
    }

    @Override
    public <E, T extends Collection<E>> T parse(String json, Class<T> collectionClass, Class<E> elementClass) throws Throwable {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
    }

}
