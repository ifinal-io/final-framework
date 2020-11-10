package org.finalframework.context.initializer;


import lombok.extern.slf4j.Slf4j;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.finalframework.context.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;

import java.util.Set;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 19:39:47
 * @see ApplicationContextInitializer
 * @see SpringFactory
 * @see SpringFactoryBeanDefinitionRegistryPostProcessor
 * @since 1.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public class SpringFactoryApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext context) {

        if (context instanceof AnnotationConfigRegistry) {
            ((AnnotationConfigRegistry) context).scan("org.finalframework");
        } else if (context instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context;
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents("org.finalframework");
            for (BeanDefinition beanDefinition : beanDefinitions) {

                String beanName = AnnotationBeanNameGenerator.INSTANCE.generateBeanName(beanDefinition, registry);
                if (!context.containsBeanDefinition(beanName)) {
                    registry.registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }


//        final HashSet<String> springFactories = new HashSet<>(SpringFactoriesLoader.loadFactoryNames(SpringFactory.class, getClass().getClassLoader()));
//        for (String annotationName : springFactories) {
//            try {
//                Class<?> factoryClass = Class.forName(annotationName);
//                logger.info("Register SpringFactoryBeanDefinitionRegistryPostProcessor for: {}", factoryClass.getCanonicalName());
//                context.addBeanFactoryPostProcessor(new SpringFactoryBeanDefinitionRegistryPostProcessor<>(factoryClass));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }

    }
}

