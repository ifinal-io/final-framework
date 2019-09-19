package org.finalframework.spring.web.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.finalframework.json.JsonException;
import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.exception.JsonResultExceptionHandler;
import org.finalframework.spring.web.exception.MissingServletRequestParameterResultExceptionHandler;
import org.finalframework.spring.web.exception.RestExceptionHandlerConfigurer;
import org.finalframework.spring.web.reponse.ResponseBodySerializer;
import org.finalframework.spring.web.reponse.RestResponseBodyAdvice;
import org.finalframework.spring.web.reponse.ResultResponseBodyInterceptor;
import org.finalframework.spring.web.reponse.ResultResponseBodySerializer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
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
public class RestResponseBodyAdviceAutoConfiguration implements ApplicationContextAware, InitializingBean {
    private final ResponseBodyAdviceProperties properties;

    private ApplicationContext applicationContext;

    public RestResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties properties) {
        this.properties = properties;
    }

    @Resource
    private ResultGlobalResultExceptionHandler resultGlobalResultExceptionHandler;
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public ResultResponseBodySerializer resultResponseBodySerializer() {
        return new ResultResponseBodySerializer();
    }


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


    @Override
    public void afterPropertiesSet() throws Exception {
        SimpleModule module = new SimpleModule();
        ResponseBodySerializer bodySerializer = applicationContext.getBean(properties.getSerializer());
        module.addSerializer(bodySerializer.type(), bodySerializer);
        objectMapper.registerModule(module);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
