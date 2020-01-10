package org.finalframework.spring.aop.interceptor;

import org.finalframework.data.util.Beans;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.annotation.OperationComponent;
import org.finalframework.spring.aop.annotation.OperationExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 15:51
 * @since 1.0
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//@SpringConfiguration
public class OperationInterceptorAutoConfiguration implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptorAutoConfiguration.class);


    private ApplicationContext applicationContext;

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
        return new OperationConfiguration();
    }

    @Bean
    public OperationInterceptor operationInterceptor() {
        return new OperationInterceptor(operationConfiguration());
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        OperationConfiguration operationConfiguration = operationConfiguration();

        Beans.findAllBeansAnnotatedBy(applicationContext, OperationExecutor.class)
                .map(it -> (Executor) it)
                .forEach(it -> {
                    OperationExecutor annotation = AnnotationUtils.findAnnotation(it.getClass(), OperationExecutor.class);
                    Class clazz = Executor.class == annotation.value() ? it.getClass() : annotation.value();
                    logger.info("==> register executor: clazz={},value={}", clazz.getSimpleName(), it.getClass().getSimpleName());
                    operationConfiguration.registerExecutor(clazz, it);
                });

        Beans.findAllBeansAnnotatedBy(applicationContext, OperationComponent.class)
                .map(it -> {
                    Class<?> clazz = it.getClass();
                    OperationComponent operationComponent = AnnotationUtils.findAnnotation(clazz, OperationComponent.class);
                    Order order = AnnotationUtils.findAnnotation(clazz, Order.class);
                    return org.finalframework.spring.aop.OperationComponent.builder()
                            .annotation(operationComponent.annotation())
                            .builder(operationComponent.builder())
                            .handler(operationComponent.handler())
                            .invocation(operationComponent.invocation())
                            .order(order == null ? 0 : order.value())
                            .build();
                })
                .sorted()
                .peek(it -> {
                    logger.info("==> register operation component: {}", it);
                })
                .forEach(operationConfiguration::registerCacheComponent);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
