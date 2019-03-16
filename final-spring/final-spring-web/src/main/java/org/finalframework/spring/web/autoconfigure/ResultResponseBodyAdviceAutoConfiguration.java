package org.finalframework.spring.web.autoconfigure;

import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.reponse.DefaultResponseInterceptor;
import org.finalframework.spring.web.reponse.ResultResponseBodyAdvice;
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
public class ResultResponseBodyAdviceAutoConfiguration {
    private final ResponseBodyAdviceProperties responseBodyAdviceProperties;

    public ResultResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties responseBodyAdviceProperties) {
        this.responseBodyAdviceProperties = responseBodyAdviceProperties;
    }

    @Bean
    public ResultResponseBodyAdvice resultResponseBodyAdvice() {
        return new ResultResponseBodyAdvice(responseBodyAdviceProperties);
    }

    @Bean
    public DefaultResponseInterceptor defaultResponseInterceptor() {
        return new DefaultResponseInterceptor();
    }

}
