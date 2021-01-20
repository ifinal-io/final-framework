package org.ifinal.finalframework.context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * AnnotationConfigApplicationContextTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@ComponentScan
class AnnotationConfigApplicationContextTest {

    @Test
    void context() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AnnotationConfigApplicationContextTest.class);
        for (final String name : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(name);
            logger.info("{}={}", name, beanDefinition.getBeanClassName());
        }
        context.refresh();
        for (final String name : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(name);
            logger.info("{}={}", name, beanDefinition.getBeanClassName());
        }
        AnnotationConfigApplicationContextTest bean = context.getBean(AnnotationConfigApplicationContextTest.class);

        Assertions.assertEquals(AnnotationConfigApplicationContextTest.class, bean.getClass());

        Assertions.assertNotNull(context.getBean(HelloBean.class));
    }

}
