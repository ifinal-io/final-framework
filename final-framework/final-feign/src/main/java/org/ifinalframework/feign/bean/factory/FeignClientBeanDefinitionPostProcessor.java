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
 * @see org.springframework.cloud.openfeign.FeignClientsRegistrar
 * @since 1.6.0
 * @see org.ifinalframework.feign.javassist.FeignClientsRegistrarJavaAssistProcessor
 **/
@Slf4j
@Component
public class FeignClientBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String DEFAULT_GATEWAY_URL = "${spring.cloud.openfeign.gateway.url}";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (String beanDefinitionName : registry.getBeanDefinitionNames()) {
            final BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);

            final String beanClassName = beanDefinition.getBeanClassName();
            if (FeignClientFactoryBean.class.getCanonicalName().equals(beanClassName)) {

                final MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();

                final PropertyValue contextId = propertyValues.getPropertyValue("contextId");
                final PropertyValue name = propertyValues.getPropertyValue("name");
                final PropertyValue url = propertyValues.getPropertyValue("url");

                if (!hasPropertyValue(url) && !hasPropertyValue(name)) {
                    // 没有配置 name 和 url，使用默认的 url
                    removeAndReplacePropertyValue(propertyValues,url,"url",DEFAULT_GATEWAY_URL);
                }

                removeAndReplacePropertyValue(propertyValues,contextId,"contextId",beanClassName);
                removeAndReplacePropertyValue(propertyValues,name,"name",beanClassName);

            }


        }
    }

    private void removeAndReplacePropertyValue(MutablePropertyValues propertyValues,PropertyValue propertyValue, String name,String value ) {
        if (!hasPropertyValue(propertyValue)) {
            if(Objects.nonNull(propertyValue)) {
                propertyValues.removePropertyValue(propertyValue);
                propertyValues.add(name, value);
            }
            logger.info("==> set FeignClientFactoryBean {}={}", name,value);
        }
    }

    private boolean hasPropertyValue(PropertyValue propertyValue){
        return Objects.nonNull(propertyValue) && !"".equals(propertyValue.getValue());
    }
}
