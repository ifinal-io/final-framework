package org.finalframework.spring.web.autoconfigure;

import org.finalframework.json.JsonException;
import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.exception.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.ConstraintViolationException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:45:31
 * @since 1.0
 */
@Configuration
@AutoConfiguration
@EnableConfigurationProperties(RestExceptionHandlerProperties.class)
@SuppressWarnings("all")
public class RestExceptionHandlerAutoConfiguration {

    private final RestExceptionHandlerProperties properties;

    public RestExceptionHandlerAutoConfiguration(RestExceptionHandlerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnClass(JsonException.class)
    public JsonResultExceptionHandler jsonResultExceptionHandler() {
        return new JsonResultExceptionHandler();
    }

    @Bean
    @ConditionalOnClass(ConstraintViolationException.class)
    public ViolationResultExceptionHandler violationResultExceptionHandler() {
        return new ViolationResultExceptionHandler();
    }

    @Bean
    public DefaultResultExceptionHandler defaultResultExceptionHandler() {
        return new DefaultResultExceptionHandler();
    }

    @Bean
    public RestGlobalExceptionHandler defaultGlobalExceptionHandler() {
        return new RestGlobalExceptionHandler(properties.getLogger());
    }

    @Bean
    public RestExceptionHandlerConfigurer restExceptionHandlerConfigurer() {
        final RestExceptionHandlerConfigurer restExceptionHandlerConfigurer = new RestExceptionHandlerConfigurer();
        restExceptionHandlerConfigurer.setGlobalExceptionHandler(defaultGlobalExceptionHandler());
        return restExceptionHandlerConfigurer;
    }

}
