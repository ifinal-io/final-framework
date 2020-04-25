package org.finalframework.retrofit.autoconfigure;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 19:49:53
 * @since 1.0
 */
@Configuration
@SpringConfiguration
//@ConditionalOnBean(ObjectMapper.class)
//@ConditionalOnMissingBean(Converter.Factory.class)
public class JacksonConverterFactoryConfiguration {

    @Bean
    public JacksonConverterFactory jacksonConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }
}

