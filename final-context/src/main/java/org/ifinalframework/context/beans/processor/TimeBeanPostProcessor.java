/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.context.beans.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.lang.Nullable;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * TimeBeanPostProcessor.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class TimeBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    @Setter
    private BeanFactory beanFactory;

    @Nullable
    @Override
    public Object postProcessBeforeInstantiation(final Class<?> beanClass, final String beanName)
        throws BeansException {

        final BeanDefinition definition = ((ConfigurableBeanFactory) beanFactory)
            .getMergedBeanDefinition(beanName);

        definition.setAttribute("postProcessBeforeInstantiation", System.currentTimeMillis());

        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInstantiation(beanClass, beanName);
    }

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {
        final BeanDefinition definition = ((ConfigurableBeanFactory) beanFactory)
            .getMergedBeanDefinition(beanName);
        definition.setAttribute("postProcessAfterInstantiation", System.currentTimeMillis());
        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInstantiation(bean, beanName);
    }

    @Nullable
    @Override
    public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
        final BeanDefinition definition = ((ConfigurableBeanFactory) beanFactory)
            .getMergedBeanDefinition(beanName);
        definition.setAttribute("postProcessBeforeInitialization", System.currentTimeMillis());
        return InstantiationAwareBeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        final BeanDefinition definition = ((ConfigurableBeanFactory) beanFactory)
            .getMergedBeanDefinition(beanName);
        definition.setAttribute("postProcessAfterInitialization", System.currentTimeMillis());

        long doInstance = (Long) definition.getAttribute("postProcessAfterInstantiation") - (Long) definition
            .getAttribute("postProcessBeforeInstantiation");
        long doInit = (Long) definition.getAttribute("postProcessAfterInitialization") - (Long) definition
            .getAttribute("postProcessBeforeInitialization");

        logger.warn("bean={},实例化={},初始化={}", beanName, doInstance, doInit);

        return InstantiationAwareBeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
