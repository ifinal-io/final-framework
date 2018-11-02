package cn.com.likly.finalframework.data.json.jackson;

import cn.com.likly.finalframework.data.json.JsonException;
import cn.com.likly.finalframework.data.json.JsonRegistry;
import cn.com.likly.finalframework.data.json.JsonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

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
@Service
@ConditionalOnClass(ObjectMapper.class)
public class JacksonJsonService implements JsonService {

    @Resource
    @Setter
    private ObjectMapper objectMapper;

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
