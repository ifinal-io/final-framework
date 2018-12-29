package com.ilikly.finalframework.spring.monitor;

import com.ilikly.finalframework.coding.plugins.spring.annotation.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 13:34:32
 * @since 1.0
 */
@AutoConfiguration
public class MethodMonitorAutoConfiguration {

    @Bean
    public MethodMonitorLogger methodMonitorLogger() {
        return new MethodMonitorLogger();
    }

    @Bean
    public MethodMonitorAspect methodMonitorAspect() {
        return new MethodMonitorAspect();
    }
}
