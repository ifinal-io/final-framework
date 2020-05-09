package org.finalframework.spring.context.initializer;


import org.finalframework.coding.spring.factory.annotation.SpringFactory;
import org.finalframework.spring.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.HashSet;
import java.util.stream.Collectors;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 19:39:47
 * @since 1.0
 */
@SpringFactory(ApplicationContextInitializer.class)
public class SpringFactoryApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(SpringFactoryApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        for (String annotationName : new HashSet<>(SpringFactoriesLoader.loadFactoryNames(SpringFactory.class, getClass().getClassLoader()))) {
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

