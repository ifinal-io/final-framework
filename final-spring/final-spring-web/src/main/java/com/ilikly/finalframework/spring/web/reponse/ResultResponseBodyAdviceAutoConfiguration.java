package com.ilikly.finalframework.spring.web.reponse;

import com.ilikly.finalframework.spring.coding.AutoConfiguration;
import com.ilikly.finalframework.spring.web.autoconfigure.ResponseBodyAdviceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:14:22
 * @since 1.0
 */
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
}
