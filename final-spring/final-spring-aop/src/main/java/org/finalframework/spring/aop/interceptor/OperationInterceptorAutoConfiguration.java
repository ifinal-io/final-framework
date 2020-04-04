package org.finalframework.spring.aop.interceptor;

import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationComponent;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.annotation.OperationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 15:51
 * @since 1.0
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@SpringConfiguration
public class OperationInterceptorAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptorAutoConfiguration.class);

    private ApplicationContext applicationContext;

    private final List<OperationComponent> operationComponents;
    private final List<Executor> executors;

    public OperationInterceptorAutoConfiguration(ObjectProvider<List<OperationComponent>> operationComponentsProvider,
                                                 ObjectProvider<List<Executor>> executorsProvider) {
        this.operationComponents = operationComponentsProvider.getIfAvailable();
        this.executors = executorsProvider.getIfAvailable();

    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public OperationSourceAdvisor cacheOperationSourceAdvisor() {
        final OperationSourceAdvisor advisor = new OperationSourceAdvisor(operationConfiguration());
        advisor.setAdviceBeanName("operationInterceptor");
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public OperationConfiguration operationConfiguration() {
        final OperationConfiguration configuration = new OperationConfiguration();
        for (Executor executor : executors) {
            OperationExecutor annotation = AnnotationUtils.findAnnotation(executor.getClass(), OperationExecutor.class);
            Class clazz = Executor.class == annotation.value() ? executor.getClass() : annotation.value();
            logger.info("==> register executor: clazz={},value={}", clazz.getSimpleName(), executor.getClass().getSimpleName());
            configuration.registerExecutor(clazz, executor);
        }

        for (OperationComponent component : operationComponents) {
            logger.info("==> register operation component: {}", component);
            configuration.registerCacheComponent(component);
        }

        return configuration;
    }

    @Bean
    public OperationInterceptor operationInterceptor(OperationConfiguration operationConfiguration) {
        return new OperationInterceptor(operationConfiguration);
    }


}
