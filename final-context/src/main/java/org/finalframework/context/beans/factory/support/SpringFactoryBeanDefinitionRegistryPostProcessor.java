/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.context.beans.factory.support;


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

import java.util.List;

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
        List<String> factories = SpringFactoriesLoader.loadFactoryNames(factoryInterface, this.getClass().getClassLoader());
        for (String factory : factories) {
            try {
                Class<?> item = Class.forName(factory);
                AnnotatedBeanDefinition annotatedBeanDefinition = new AnnotatedGenericBeanDefinition(item);
                BeanDefinitionHolder holder = new BeanDefinitionHolder(annotatedBeanDefinition, beanNameGenerator.generateBeanName(annotatedBeanDefinition, registry));
                BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

