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

package org.finalframework.context.initializer;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * Registration a {@code framework} class into {@link ConfigurableApplicationContext}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class AbsFrameworkApplicationContextInitializer<C extends ConfigurableApplicationContext> implements
    ApplicationContextInitializer<C> {

    private final Class<?> framework;

    protected AbsFrameworkApplicationContextInitializer(final Class<?> framework) {
        this.framework = Objects.requireNonNull(framework);
    }

    @Override
    public void initialize(final @NonNull C context) {
        BeanDefinitionReaderUtils.registerWithGeneratedName(new AnnotatedGenericBeanDefinition(framework),
            getBeanDefinitionRegistry(context));
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
