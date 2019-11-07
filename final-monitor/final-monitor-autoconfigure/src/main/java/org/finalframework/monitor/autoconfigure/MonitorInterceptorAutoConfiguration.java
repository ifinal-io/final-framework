package org.finalframework.monitor.autoconfigure;

import org.finalframework.monitor.action.ActionContextLoggerHandler;
import org.finalframework.monitor.interceptor.DefaultRecorder;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:15:49
 * @since 1.0
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@SpringConfiguration
@SuppressWarnings("unused")
@EnableConfigurationProperties(MonitorProperties.class)
public class MonitorInterceptorAutoConfiguration {

    private final MonitorProperties properties;

    public MonitorInterceptorAutoConfiguration(MonitorProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DefaultRecorder actionRecorder() {
        final DefaultRecorder recorder = new DefaultRecorder();
        recorder.setDefaultActionContextHandler(actionContextLoggerHandler());
        return recorder;
    }

    @Bean
    public ActionContextLoggerHandler actionContextLoggerHandler() {
        return new ActionContextLoggerHandler(properties.getAction().getLogger());
    }


}
