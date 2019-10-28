package org.finalframework.data.autoconfigure;

import org.finalframework.coding.spring.AutoConfiguration;
import org.finalframework.data.exception.result.IExceptionResultExceptionHandler;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.data.exception.result.UnCatchResultExceptionHandler;
import org.finalframework.data.exception.result.ViolationResultExceptionHandler;
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
@EnableConfigurationProperties(ResultExceptionHandlerProperties.class)
@SuppressWarnings("all")
public class ResultExceptionHandlerAutoConfiguration {

    private final ResultExceptionHandlerProperties properties;

    public ResultExceptionHandlerAutoConfiguration(ResultExceptionHandlerProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnClass(ConstraintViolationException.class)
    public ViolationResultExceptionHandler violationResultExceptionHandler() {
        return new ViolationResultExceptionHandler();
    }

    @Bean
    public IExceptionResultExceptionHandler iExceptionResultExceptionHandler() {
        return new IExceptionResultExceptionHandler();
    }

    @Bean
    public UnCatchResultExceptionHandler unCatchResultExceptionHandler() {
        return new UnCatchResultExceptionHandler();
    }

    @Bean
    public ResultGlobalResultExceptionHandler resultGlobalResultExceptionHandler() {
        final ResultGlobalResultExceptionHandler restExceptionHandlerConfigurer = new ResultGlobalResultExceptionHandler(properties.getLogger());
        restExceptionHandlerConfigurer.setUnCatchExceptionHandler(unCatchResultExceptionHandler());
        return restExceptionHandlerConfigurer;
    }

}
