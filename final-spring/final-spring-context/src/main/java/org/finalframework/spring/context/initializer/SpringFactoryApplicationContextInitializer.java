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

package org.finalframework.spring.context.initializer;


import java.util.HashSet;

import org.finalframework.spring.annotation.factory.SpringFactory;
import org.finalframework.spring.beans.factory.support.SpringFactoryBeanDefinitionRegistryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.support.SpringFactoriesLoader;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-06 19:39:47
 * @since 1.0
 */
@SpringFactory(ApplicationContextInitializer.class)
public class SpringFactoryApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(SpringFactoryApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        final HashSet<String> springFactories = new HashSet<>(SpringFactoriesLoader.loadFactoryNames(SpringFactory.class, getClass().getClassLoader()));
        for (String annotationName : springFactories) {
            try {
                Class<?> factoryClass = Class.forName(annotationName);
                logger.info("Register SpringFactoryBeanDefinitionRegistryPostProcessor for: {}", factoryClass.getCanonicalName());
                applicationContext.addBeanFactoryPostProcessor(new SpringFactoryBeanDefinitionRegistryPostProcessor<>(factoryClass));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}

