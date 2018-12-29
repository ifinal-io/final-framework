package com.ilikly.finalframework.spring.handler.exception;

import com.ilikly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:45:31
 * @since 1.0
 */
@AutoConfiguration
public class RestExceptionHandlerAutoConfiguration {

    @Bean
    public JsonResultExceptionHandler jsonResultExceptionHandler() {
        return new JsonResultExceptionHandler();
    }

    @Bean
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
