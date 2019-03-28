package org.finalframework.monitor.action.interceptor;

import org.finalframework.monitor.action.ActionConfiguration;
import org.finalframework.spring.aop.interceptor.OperationSourceAdvisor;
import org.finalframework.spring.coding.AutoConfiguration;
import org.springframework.beans.factory.config.BeanDefinition;
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
public class ActionInterceptorAutoConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public OperationSourceAdvisor actionOperationSourceAdvisor() {
        final OperationSourceAdvisor advisor = new OperationSourceAdvisor(actionConfiguration());
        advisor.setAdviceBeanName("actionInterceptor");
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ActionConfiguration actionConfiguration() {
        final ActionConfiguration configuration = new ActionConfiguration();
        return configuration;
    }

    @Bean
    public ActionInterceptor actionInterceptor() {
        return new ActionInterceptor(actionConfiguration());
    }

}
