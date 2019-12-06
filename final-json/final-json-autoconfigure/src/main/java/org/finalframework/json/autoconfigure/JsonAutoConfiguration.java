package org.finalframework.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.finalframework.json.JsonRegistry;
import org.finalframework.json.JsonService;
import org.finalframework.json.jackson.JacksonJsonService;
import org.finalframework.json.jackson.FinalJacksonModule;
import org.finalframework.json.jackson.serializer.modifier.BeanDatePropertySerializerModifier;
import org.finalframework.json.jackson.serializer.modifier.BeanEnumPropertySerializerModifier;
import org.finalframework.json.jackson.serializer.modifier.BeanLocalDatePropertySerializerModifier;
import org.finalframework.json.jackson.serializer.modifier.BeanLocalDateTimePropertySerializerModifier;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:16:57
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@EnableConfigurationProperties(JsonProperties.class)
public class JsonAutoConfiguration {

    private final JsonProperties properties;
    @Resource
    private ObjectMapper objectMapper;

    public JsonAutoConfiguration(JsonProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void initObjectMapper() {
        objectMapper.registerModule(new FinalJacksonModule(objectMapper));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanEnumPropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanLocalDatePropertySerializerModifier()));
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(new BeanLocalDateTimePropertySerializerModifier()));
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    }

    @PostConstruct
    public void init() {
        if (properties.getJsonService() != null) {
            try {
                JsonService jsonService = properties.getJsonService().newInstance();
                if (jsonService instanceof JacksonJsonService) {
                    ((JacksonJsonService) jsonService).setObjectMapper(objectMapper);
                }
                JsonRegistry.getInstance().register(jsonService);
            } catch (Exception e) {

            }

        }
    }


}
