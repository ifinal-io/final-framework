package org.finalframework.boot.autoconfigure.web.trace;

import org.finalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.finalframework.web.interceptor.TraceHandlerInterceptor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/16 18:06:26
 * @since 1.0
 */
@Configuration
@SpringAutoConfiguration
@ConditionalOnBean(TraceHandlerInterceptor.class)
@EnableConfigurationProperties(TraceProperties.class)
public class TraceHandlerInterceptorAutoConfiguration implements InitializingBean {

    private final TraceProperties properties;
    private final TraceHandlerInterceptor traceHandlerInterceptor;

    public TraceHandlerInterceptorAutoConfiguration(TraceProperties properties, TraceHandlerInterceptor traceHandlerInterceptor) {
        this.properties = properties;
        this.traceHandlerInterceptor = traceHandlerInterceptor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        traceHandlerInterceptor.setTraceName(properties.getName());
        traceHandlerInterceptor.setTraceName(properties.getParam());
        traceHandlerInterceptor.setHeaderName(properties.getHeader());
    }
}
