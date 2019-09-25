package org.finalframework.spring.web.interceptor;

import org.finalframework.spring.coding.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:07:07
 * @since 1.0
 */
@AutoConfiguration
@Configuration
public class HandlerInterceptorAutoConfiguration {


    @Bean
    public TraceHandlerInterceptor traceHandlerInterceptor() {
        return new TraceHandlerInterceptor();
    }

    @Bean
    public HandlerInterceptorConfigurer handlerInterceptorConfigurer() {
        return new HandlerInterceptorConfigurer();
    }


}



