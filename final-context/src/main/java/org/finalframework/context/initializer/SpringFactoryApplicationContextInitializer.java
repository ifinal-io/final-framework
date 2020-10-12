package org.finalframework.context.initializer;


import lombok.extern.slf4j.Slf4j;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.finalframework.context.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;

import java.util.HashSet;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 19:39:47
 * @since 1.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public class SpringFactoryApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {

        final HashSet<String> springFactories = new HashSet<>(SpringFactoriesLoader.loadFactoryNames(SpringFactory.class, getClass().getClassLoader()));
        for (String annotationName : springFactories) {
            try {
                Class<?> factoryClass = Class.forName(annotationName);
                logger.info("Register SpringFactoryBeanDefinitionRegistryPostProcessor for: {}", factoryClass.getCanonicalName());
                applicationContext.addBeanFactoryPostProcessor(new SpringFactoryBeanDefinitionRegistryPostProcessor<>(factoryClass));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}

