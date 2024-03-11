/*
 * Copyright 2020-2024 the original author or authors.
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

package org.ifinalframework.feign.bean.factory;


import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.cloud.openfeign.FeignClientFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * FeignClientBean
 *
 * @author iimik
 * @since 1.6.0
 * @see org.springframework.cloud.openfeign.FeignClientsRegistrar
 **/
@Slf4j
@Component
public class FeignClientBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String beanDefinitionName : registry.getBeanDefinitionNames()) {
            final BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);

            if (FeignClientFactoryBean.class.getCanonicalName().equals(beanDefinition.getBeanClassName())) {

                final MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();

                final PropertyValue contextId = propertyValues.getPropertyValue("contextId");
                if (Objects.nonNull(contextId) && "".equals(contextId.getValue())) {
                    propertyValues.removePropertyValue(contextId);
                    propertyValues.add("contextId", beanDefinitionName);
                    logger.info("set FeignClientFactoryBean contextId={}", beanDefinitionName);
                }

                final PropertyValue name = propertyValues.getPropertyValue("name");
                if (Objects.nonNull(name) && "".equals(name.getValue())) {
                    propertyValues.removePropertyValue(name);
                    propertyValues.add("name", beanDefinitionName);
                    logger.info("set FeignClientFactoryBean name={}", beanDefinitionName);
                }

            }


        }
    }
}
