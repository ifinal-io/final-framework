package org.finalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/10 14:07:41
 * @since 1.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public class FinalFrameworkApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String[] FINAL_FRAMEWORK_BASE_PACKAGES = new String[]{"org.finalframework"};


    @Override
    public void initialize(@NonNull ConfigurableApplicationContext context) {
        if (context instanceof AnnotationConfigRegistry) {
            ((AnnotationConfigRegistry) context).scan(FINAL_FRAMEWORK_BASE_PACKAGES);
        } else if (context instanceof BeanDefinitionRegistry) {
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context;
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);

            Arrays.stream(FINAL_FRAMEWORK_BASE_PACKAGES)
                    .forEach(basePackage -> {
                        Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(basePackage);
                        for (BeanDefinition beanDefinition : beanDefinitions) {
                            String beanName = AnnotationBeanNameGenerator.INSTANCE.generateBeanName(beanDefinition, registry);
                            if (!context.containsBeanDefinition(beanName)) {
                                registry.registerBeanDefinition(beanName, beanDefinition);
                            }
                        }
                    });


        }

    }
}
