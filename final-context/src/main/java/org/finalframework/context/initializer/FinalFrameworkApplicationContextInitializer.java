package org.finalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.FinalFramework;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/10 14:07:41
 * @since 1.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public class FinalFrameworkApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext context) {
        BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(FinalFramework.class), getBeanDefinitionRegistry(context));
    }

    @NonNull
    private BeanDefinitionRegistry getBeanDefinitionRegistry(@NonNull ConfigurableApplicationContext context) {
        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context;
        }

        if (context.getBeanFactory() instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context.getBeanFactory();
        }

        throw new BeanDefinitionStoreException("Can not found BeanDefinitionRegistry from " + context);

    }
}
