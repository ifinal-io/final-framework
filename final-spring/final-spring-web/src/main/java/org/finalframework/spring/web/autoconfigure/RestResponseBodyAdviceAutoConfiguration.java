package org.finalframework.spring.web.autoconfigure;

import io.lettuce.core.RedisConnectionException;
import org.finalframework.coding.spring.AutoConfiguration;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.json.JsonException;
import org.finalframework.spring.web.exception.RestExceptionHandlerConfigurer;
import org.finalframework.spring.web.exception.result.JsonExceptionHandler;
import org.finalframework.spring.web.exception.result.MissingServletParameterResultExceptionHandler;
import org.finalframework.spring.web.exception.result.RedisConnectExceptionHandler;
import org.finalframework.spring.web.reponse.advice.EnumsResponseBodyAdvice;
import org.finalframework.spring.web.reponse.advice.PageResponseBodyAdvice;
import org.finalframework.spring.web.reponse.advice.ResponsibleResponseBodyAdvice;
import org.finalframework.spring.web.reponse.advice.ResultResponseBodyAdvice;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:14:22
 * @since 1.0
 */
@Configuration
@AutoConfiguration
@EnableConfigurationProperties(ResponseBodyAdviceProperties.class)
public class RestResponseBodyAdviceAutoConfiguration implements ApplicationContextAware {
    private final ResponseBodyAdviceProperties properties;

    private ApplicationContext applicationContext;

    public RestResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties properties) {
        this.properties = properties;
    }

    @Resource
    private ResultGlobalResultExceptionHandler resultGlobalResultExceptionHandler;

    @Bean
    @ConditionalOnClass(JsonException.class)
    public JsonExceptionHandler jsonResultExceptionHandler() {
        return new JsonExceptionHandler();
    }

    @Bean
    @ConditionalOnClass(RedisConnectionException.class)
    public RedisConnectExceptionHandler redisConnectExceptionHandler() {
        return new RedisConnectExceptionHandler();
    }


    @Bean
    public MissingServletParameterResultExceptionHandler missingServletRequestParameterResultExceptionHandler() {
        return new MissingServletParameterResultExceptionHandler();
    }


    @Bean
    public RestExceptionHandlerConfigurer restExceptionHandlerConfigurer() {
        final RestExceptionHandlerConfigurer restExceptionHandlerConfigurer = new RestExceptionHandlerConfigurer();
        restExceptionHandlerConfigurer.setGlobalExceptionHandler(resultGlobalResultExceptionHandler);
        return restExceptionHandlerConfigurer;
    }


    @Bean
    public EnumsResponseBodyAdvice enumsResponseBodyAdvice() {
        return new EnumsResponseBodyAdvice();
    }

    @Bean
    public PageResponseBodyAdvice pageResponseBodyAdvice() {
        return new PageResponseBodyAdvice();
    }

    @Bean
    public ResultResponseBodyAdvice resultResponseBodyAdvice() {
        return new ResultResponseBodyAdvice();
    }

    @Bean
    public ResponsibleResponseBodyAdvice responsibleResponseBodyAdvice() {
        ResponsibleResponseBodyAdvice advice = new ResponsibleResponseBodyAdvice();
        advice.setSyncStatus(properties.isSyncStatus());
        return advice;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
