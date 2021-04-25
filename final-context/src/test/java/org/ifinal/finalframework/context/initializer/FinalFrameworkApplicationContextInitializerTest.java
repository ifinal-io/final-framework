package org.ifinal.finalframework.context.initializer;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.ifinal.finalframework.FinalFramework;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * FinalFrameworkApplicationContextInitializerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class FinalFrameworkApplicationContextInitializerTest {

    @Test
    void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        new FinalFrameworkApplicationContextInitializer().initialize(context);
        context.refresh();
        FinalFramework framework = context.getBean(FinalFramework.class);
        assertNotNull(framework);
        logger.info("framework:{}", framework.getBeanName());
    }

}
