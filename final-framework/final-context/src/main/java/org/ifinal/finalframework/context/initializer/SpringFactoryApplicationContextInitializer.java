package org.ifinal.finalframework.context.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

import org.ifinal.auto.spring.factory.annotation.SpringFactory;
import org.ifinal.finalframework.context.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0.0
 * @see SpringFactory
 * @see ApplicationContextInitializer
 * @see SpringFactoryBeanDefinitionRegistryPostProcessor
 * @since 1.0.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public final class SpringFactoryApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Class<SpringFactory> springFactory = SpringFactory.class;

    @Override
    public void initialize(final @NonNull ConfigurableApplicationContext context) {
        context.addBeanFactoryPostProcessor(new SpringFactoryBeanDefinitionRegistryPostProcessor(springFactory, true));
    }

}

