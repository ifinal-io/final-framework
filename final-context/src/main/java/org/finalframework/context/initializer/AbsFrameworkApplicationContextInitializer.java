package org.finalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
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
public abstract class AbsFrameworkApplicationContextInitializer<C extends ConfigurableApplicationContext> implements ApplicationContextInitializer<C> {

    private final Class<?> framework;

    public AbsFrameworkApplicationContextInitializer(Class<?> framework) {
        this.framework = framework;
    }

    @Override
    public void initialize(@NonNull C context) {
        BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(framework), getBeanDefinitionRegistry(context));
    }

    @NonNull
    private BeanDefinitionRegistry getBeanDefinitionRegistry(@NonNull C context) {
        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context;
        }

        if (context.getBeanFactory() instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context.getBeanFactory();
        }

        throw new BeanDefinitionStoreException("Can not found BeanDefinitionRegistry from " + context);

    }
}
