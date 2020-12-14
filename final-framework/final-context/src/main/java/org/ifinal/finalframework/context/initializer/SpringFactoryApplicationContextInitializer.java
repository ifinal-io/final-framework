package org.ifinal.finalframework.context.initializer;

import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.ifinal.finalframework.context.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

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
public class SpringFactoryApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(final @NonNull ConfigurableApplicationContext context) {

        final HashSet<String> springFactories = new HashSet<>(
            SpringFactoriesLoader.loadFactoryNames(SpringFactory.class, getClass().getClassLoader()));

        for (String annotationName : springFactories) {
            try {
                final Class<?> factoryClass = Class.forName(annotationName);
                logger.info("Register SpringFactoryBeanDefinitionRegistryPostProcessor for: {}",
                    factoryClass.getCanonicalName());
                context
                    .addBeanFactoryPostProcessor(new SpringFactoryBeanDefinitionRegistryPostProcessor<>(factoryClass));
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

}

