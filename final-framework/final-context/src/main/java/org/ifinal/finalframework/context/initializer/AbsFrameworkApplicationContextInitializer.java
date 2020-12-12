package org.ifinal.finalframework.context.initializer;

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
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class AbsFrameworkApplicationContextInitializer<C extends ConfigurableApplicationContext> implements ApplicationContextInitializer<C> {

    private final Class<?> framework;

    protected AbsFrameworkApplicationContextInitializer(final Class<?> framework) {

        this.framework = framework;
    }

    @Override
    public void initialize(final @NonNull C context) {

        BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(framework), getBeanDefinitionRegistry(context));
    }

    /**
     * find {@link BeanDefinitionRegistry} from {@link ConfigurableApplicationContext}.
     *
     * @param context the application context
     * @return the {@linkplain BeanDefinitionRegistry registry}
     */
    @NonNull
    private BeanDefinitionRegistry getBeanDefinitionRegistry(final @NonNull C context) {

        if (context instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context;
        }

        if (context.getBeanFactory() instanceof BeanDefinitionRegistry) {
            return (BeanDefinitionRegistry) context.getBeanFactory();
        }

        throw new BeanDefinitionStoreException("Can not found BeanDefinitionRegistry from " + context);

    }
}
