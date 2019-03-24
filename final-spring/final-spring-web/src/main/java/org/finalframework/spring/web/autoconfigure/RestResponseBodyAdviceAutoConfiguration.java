package org.finalframework.spring.web.autoconfigure;

import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.reponse.RestResponseBodyAdvice;
import org.finalframework.spring.web.reponse.ResultResponseBodyInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    private final ResponseBodyAdviceProperties responseBodyAdviceProperties;

    public RestResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties responseBodyAdviceProperties) {
        this.responseBodyAdviceProperties = responseBodyAdviceProperties;
    }

    @Bean
    public RestResponseBodyAdvice resultResponseBodyAdvice() {
        return new RestResponseBodyAdvice(responseBodyAdviceProperties);
    }

    @Bean
    public ResultResponseBodyInterceptor resultResponseBodyInterceptor() {
        return new ResultResponseBodyInterceptor();
    }

}
