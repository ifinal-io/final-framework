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

package org.finalframework.retrofit.autoconfigure;


import org.finalframework.retrofit.ClassPathRetrofitScanner;
import org.finalframework.retrofit.annotation.Retrofit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-25 17:20:31
 * @since 1.0
 */

public class RetrofitAutoConfiguredScannerRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(RetrofitAutoConfiguredScannerRegistrar.class);
    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.debug("Searching for retrofits annotated with @Retrofit");
        final ClassPathRetrofitScanner scanner = new ClassPathRetrofitScanner(registry);

        try {
            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }

            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                for (String pkg : packages) {
                    logger.debug("Using auto-configuration base package '{}'", pkg);
                }
            }
            scanner.setAnnotationClass(Retrofit.class);
            scanner.doScan(StringUtils.toStringArray(packages));
        } catch (IllegalStateException ex) {
            logger.debug("Could not determine auto-configuration package, automatic retrofit scanning disabled.", ex);
        }
    }
}

