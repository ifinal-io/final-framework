package org.finalframework.json.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.finalframework.data.converter.EnumClassConverter;
import org.finalframework.data.converter.EnumConverter;
import org.finalframework.json.JsonRegistry;
import org.finalframework.json.JsonService;
import org.finalframework.json.jackson.JacksonJsonService;
import org.finalframework.json.jackson.ObjectMapperFactory;
import org.finalframework.json.jackson.serializer.ClassJsonSerializer;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
    private final EnumClassConverter enumClassConverter;
    @Resource
    private ObjectMapper objectMapper;

    public JsonAutoConfiguration(JsonProperties properties, ObjectProvider<EnumClassConverter> enumConverterProvider) {
        this.properties = properties;
        this.enumClassConverter = enumConverterProvider.getIfAvailable();
    }

    @PostConstruct
    public void initObjectMapper() {
        new ObjectMapperFactory(objectMapper, enumClassConverter);

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
