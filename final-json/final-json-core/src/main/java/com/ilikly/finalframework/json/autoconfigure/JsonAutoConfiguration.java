package com.ilikly.finalframework.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ilikly.finalframework.json.JsonRegistry;
import com.ilikly.finalframework.json.JsonService;
import com.ilikly.finalframework.json.jackson.JacksonJsonService;
import com.ilikly.finalframework.json.jackson.JavaTimeModule;
import com.ilikly.finalframework.spring.coding.AutoConfiguration;
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
@AutoConfiguration
@EnableConfigurationProperties(JsonProperties.class)
@Configuration
public class JsonAutoConfiguration {

    private final JsonProperties properties;
    @Resource
    private ObjectMapper objectMapper;

    public JsonAutoConfiguration(JsonProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void initObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
//        objectMapper.registerModule(new SimpleModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
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
