package org.springframework.boot.autoconfigure;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.AnnotationMetadata;

import org.ifinal.finalframework.ContextApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * AutoConfigurationImportSelectorTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 * @see org.springframework.context.annotation.ImportSelector
 */
@Slf4j
class AutoConfigurationImportSelectorTest {
    @Test
    void selectImports(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        AutoConfigurationImportSelector selector = new AutoConfigurationImportSelector();
        selector.setEnvironment(context.getEnvironment());
        selector.setResourceLoader(context);
        selector.setBeanFactory(context.getBeanFactory());
        String[] imports = selector.selectImports(AnnotationMetadata.introspect(ContextApplicationContext.class));
        logger.info("{}",imports);
    }

}
