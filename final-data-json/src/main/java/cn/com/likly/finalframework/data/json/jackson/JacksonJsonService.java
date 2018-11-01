package cn.com.likly.finalframework.data.json.jackson;

import cn.com.likly.finalframework.data.json.JsonException;
import cn.com.likly.finalframework.data.json.JsonRegistry;
import cn.com.likly.finalframework.data.json.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:39
 * @since 1.0
 */
@Service
@ConditionalOnClass(ObjectMapper.class)
public class JacksonJsonService implements JsonService {

    @Resource
    @Setter
    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        JsonRegistry.getInstance().registerJsonService(this);
    }

    @Override
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <T> T parse(String json, Class<T> elementClass) {
        try {
            return objectMapper.readValue(json, elementClass);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    @Override
    public <E, T extends Collection<E>> T parse(String json, Class<T> collectionClass, Class<E> elementClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass));
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

}
