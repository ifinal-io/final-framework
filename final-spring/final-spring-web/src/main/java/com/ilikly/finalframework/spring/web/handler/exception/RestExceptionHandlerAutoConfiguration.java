package com.ilikly.finalframework.spring.web.handler.exception;

import com.ilikly.finalframework.json.JsonException;
import com.ilikly.finalframework.spring.coding.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import javax.validation.ConstraintViolationException;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:45:31
 * @since 1.0
 */
@AutoConfiguration
public class RestExceptionHandlerAutoConfiguration {

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
    DefaultResultExceptionHandler defaultResultExceptionHandler() {
        return new DefaultResultExceptionHandler();
    }

    @Bean
    public RestExceptionHandlerConfigurer restExceptionHandlerConfigurer() {
        return new RestExceptionHandlerConfigurer();
    }

}
