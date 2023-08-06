/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.context.resource;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import org.ifinalframework.context.annotation.ResourceValue;
import org.ifinalframework.json.Json;

import java.util.Arrays;
import java.util.Collection;

/**
 * BeanFactoryResourceValueManager.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class BeanFactoryResourceValueManager implements ResourceValueManager, ApplicationContextAware {

    private final MultiValueMap<String, ResourceValueHolder> cache = new LinkedMultiValueMap<>(256);

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        Arrays.stream(applicationContext.getBeanNamesForAnnotation(ResourceValue.class))
                .map(applicationContext::getBean)
                .forEach(bean -> {
                    Class<?> targetClass = AopUtils.getTargetClass(bean);

                    for (final ResourceValueHolder holder : ResourceValueUtils.findAllResourceValueHolders(bean, targetClass)) {
                        addResourceValueHolder(holder.getKey(), holder);
                    }

                });
    }

    @Override
    public Collection<ResourceValueHolder> getResourceValueHolders(final String key) {
        return cache.get(key);
    }

    @Override
    public void addResourceValueHolder(final String key, final ResourceValueHolder holder) {
        cache.add(key, holder);
    }

    @Override
    public void setValue(final String key, final String value) {
        cache.get(key).forEach(holder -> holder.setValue(Json.toObject(value, holder.getValueType())));
    }

}
