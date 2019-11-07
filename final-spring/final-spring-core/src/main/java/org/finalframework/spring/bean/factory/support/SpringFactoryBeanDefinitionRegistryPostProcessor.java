package org.finalframework.spring.bean.factory.support;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.support.SpringFactoriesLoader;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 18:27:39
 * @since 1.0
 */
public class SpringFactoryBeanDefinitionRegistryPostProcessor<T> implements BeanDefinitionRegistryPostProcessor {
    private final Class<T> factoryInterface;

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public SpringFactoryBeanDefinitionRegistryPostProcessor(Class<T> factoryInterface) {
        this.factoryInterface = factoryInterface;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String factory : SpringFactoriesLoader.loadFactoryNames(factoryInterface, this.getClass().getClassLoader())) {
            try {
                Class<?> item = Class.forName(factory);
                AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedGenericBeanDefinition(item);
                BeanDefinitionHolder holder = new BeanDefinitionHolder(annotatedBeanDefinition, beanNameGenerator.generateBeanName(annotatedBeanDefinition, registry));
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

