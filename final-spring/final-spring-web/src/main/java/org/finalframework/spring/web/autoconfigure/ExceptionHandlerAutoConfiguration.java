package org.finalframework.spring.web.autoconfigure;


import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.finalframework.spring.web.exception.RestControllerExceptionHandler;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 11:10:39
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@EnableConfigurationProperties(ExceptionHandlerProperties.class)
public class ExceptionHandlerAutoConfiguration implements ApplicationContextAware {
    private final ExceptionHandlerProperties properties;
    private ApplicationContext applicationContext;

    public ExceptionHandlerAutoConfiguration(ExceptionHandlerProperties properties) {
        this.properties = properties;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public RestControllerExceptionHandler restControllerExceptionHandler() {
        return new RestControllerExceptionHandler();
    }

    @PostConstruct
    public void init() {
        final RestControllerExceptionHandler restControllerExceptionHandler = restControllerExceptionHandler();
        restControllerExceptionHandler.setGlobalExceptionHandler(applicationContext.getBean(properties.getGlobalExceptionHandler()));
    }
}

