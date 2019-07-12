package org.finalframework.monitor.autoconfigure;

import org.finalframework.monitor.action.ActionContextLoggerHandler;
import org.finalframework.monitor.component.ActionOperationComponent;
import org.finalframework.monitor.component.AlertOperationComponent;
import org.finalframework.monitor.component.TraceOperationComponent;
import org.finalframework.monitor.executor.MDCTracer;
import org.finalframework.monitor.interceptor.DefaultRecorder;
import org.finalframework.spring.coding.AutoConfiguration;
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
@AutoConfiguration
@SuppressWarnings("unused")
@EnableConfigurationProperties(MonitorProperties.class)
public class MonitorInterceptorAutoConfiguration {

    private final MonitorProperties properties;

    public MonitorInterceptorAutoConfiguration(MonitorProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ActionOperationComponent actionOperationComponent() {
        return new ActionOperationComponent();
    }


    @Bean
    public TraceOperationComponent traceOperationComponent() {
        return new TraceOperationComponent();
    }

    @Bean
    public AlertOperationComponent alertOperationComponent() {
        return new AlertOperationComponent();
    }

    @Bean
    public MDCTracer mdcTracer() {
        return new MDCTracer();
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
