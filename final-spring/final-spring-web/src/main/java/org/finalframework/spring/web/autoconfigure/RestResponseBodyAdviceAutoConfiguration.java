package org.finalframework.spring.web.autoconfigure;

import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.json.JsonException;
import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.exception.JsonResultExceptionHandler;
import org.finalframework.spring.web.exception.MissingServletRequestParameterResultExceptionHandler;
import org.finalframework.spring.web.exception.RestExceptionHandlerConfigurer;
import org.finalframework.spring.web.reponse.RestResponseBodyAdvice;
import org.finalframework.spring.web.reponse.ResultResponseBodyInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
public class RestResponseBodyAdviceAutoConfiguration {
    private final ResponseBodyAdviceProperties properties;

    public RestResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties properties) {
        this.properties = properties;
    }

    @Resource
    private ResultGlobalResultExceptionHandler resultGlobalResultExceptionHandler;

    @Bean
    public RestResponseBodyAdvice resultResponseBodyAdvice() {
        return new RestResponseBodyAdvice(properties);
    }

    @Bean
    public ResultResponseBodyInterceptor resultResponseBodyInterceptor() {
        return new ResultResponseBodyInterceptor();
    }

    @Bean
    @ConditionalOnClass(JsonException.class)
    public JsonResultExceptionHandler jsonResultExceptionHandler() {
        return new JsonResultExceptionHandler();
    }

    @Bean
    public MissingServletRequestParameterResultExceptionHandler missingServletRequestParameterResultExceptionHandler() {
        return new MissingServletRequestParameterResultExceptionHandler();
    }


    @Bean
    public RestExceptionHandlerConfigurer restExceptionHandlerConfigurer() {

        final RestExceptionHandlerConfigurer restExceptionHandlerConfigurer = new RestExceptionHandlerConfigurer();
        restExceptionHandlerConfigurer.setGlobalExceptionHandler(resultGlobalResultExceptionHandler);
        return restExceptionHandlerConfigurer;
    }


}
