package com.ilikly.finalframework.spring.web.reponse;

import com.ilikly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:14:22
 * @since 1.0
 */
@AutoConfiguration
public class ResultResponseBodyAdviceAutoConfiguration {
    @Bean
    public ResultResponseBodyAdvice resultResponseBodyAdvice() {
        return new ResultResponseBodyAdvice();
    }
}
