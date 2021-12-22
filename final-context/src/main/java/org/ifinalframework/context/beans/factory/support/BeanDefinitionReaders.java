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

package org.ifinalframework.context.beans.factory.support;

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.SpringProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * BeanDefinitionReaders.
 *
 * @author likly
 * @version 1.2.4
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromImportedResources(Map)
 * @since 1.2.4
 */
public final class BeanDefinitionReaders {
    private static final Map<Class<? extends BeanDefinitionReader>, BeanDefinitionReader> instances = new LinkedHashMap<>();

    private static final boolean shouldIgnoreXml = SpringProperties.getFlag("spring.xml.ignore");

    private BeanDefinitionReaders() {
    }

    /**
     * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromImportedResources
     */
    public static Class<? extends BeanDefinitionReader> reader(String resource) {
        if (StringUtils.endsWithIgnoreCase(resource, ".groovy")) {
            return GroovyBeanDefinitionReader.class;
        }

        if (shouldIgnoreXml) {
            throw new UnsupportedOperationException("XML support disabled");
        }

        return XmlBeanDefinitionReader.class;

    }


    public static BeanDefinitionReader instance(String resource, BeanDefinitionRegistry registry, ResourceLoader resourceLoader, Environment environment) {
        return instance(reader(resource), registry, resourceLoader, environment);
    }

    public static BeanDefinitionReader instance(Class<? extends BeanDefinitionReader> clazz, BeanDefinitionRegistry registry, ResourceLoader resourceLoader, Environment environment) {

        return instances.computeIfAbsent(clazz, readerClass -> {
            try {
                // Instantiate the specified BeanDefinitionReader
                BeanDefinitionReader reader = readerClass.getConstructor(BeanDefinitionRegistry.class).newInstance(registry);
                // Delegate the current ResourceLoader to it if possible
                if (reader instanceof AbstractBeanDefinitionReader) {
                    AbstractBeanDefinitionReader abdr = ((AbstractBeanDefinitionReader) reader);
                    abdr.setResourceLoader(resourceLoader);
                    abdr.setEnvironment(environment);
                }
                return reader;
            } catch (Throwable ex) {
                throw new IllegalStateException(
                        "Could not instantiate BeanDefinitionReader class [" + readerClass.getName() + "]");
            }
        });

    }

}
