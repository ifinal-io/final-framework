package org.finalframework.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finalframework.json.JsonRegistry;
import org.finalframework.json.JsonService;
import org.finalframework.json.jackson.JacksonJsonService;
import org.finalframework.json.jackson.ObjectMapperFactory;
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
        new ObjectMapperFactory(objectMapper);
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
