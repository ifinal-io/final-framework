package org.finalframework.spring.web.autoconfigure;

import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:14:22
 * @since 1.0
 */
@Configuration
@SpringConfiguration
@EnableConfigurationProperties(ResponseBodyAdviceProperties.class)
public class RestResponseBodyAdviceAutoConfiguration implements ApplicationContextAware {
    private final ResponseBodyAdviceProperties properties;

    private ApplicationContext applicationContext;

    public RestResponseBodyAdviceAutoConfiguration(ResponseBodyAdviceProperties properties) {
        this.properties = properties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
