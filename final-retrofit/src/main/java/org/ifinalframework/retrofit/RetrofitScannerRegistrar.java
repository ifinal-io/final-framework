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

package org.ifinalframework.retrofit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import org.ifinalframework.retrofit.annotation.RetrofitScan;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class RetrofitScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(final @NonNull ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(final @NonNull AnnotationMetadata importingClassMetadata,
        final @NonNull BeanDefinitionRegistry registry) {

        AnnotationAttributes annotationAttributes = AnnotationAttributes
            .fromMap(importingClassMetadata.getAnnotationAttributes(RetrofitScan.class.getName()));

        Objects.requireNonNull(annotationAttributes);

        ClassPathRetrofitScanner scanner = new ClassPathRetrofitScanner(registry);

        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        Class<? extends Annotation> annotationClass = annotationAttributes.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }

        Class<?> markerInterface = annotationAttributes.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            scanner.setMarkerInterface(markerInterface);
        }

        Class<? extends BeanNameGenerator> generatorClass = annotationAttributes.getClass("nameGenerator");
        if (!BeanNameGenerator.class.equals(generatorClass)) {
            scanner.setBeanNameGenerator(BeanUtils.instantiateClass(generatorClass));
        }

        Class<? extends RetrofitFactoryBean<?>> mapperFactoryBeanClass = annotationAttributes.getClass("factoryBean");
        if (!RetrofitFactoryBean.class.equals(mapperFactoryBeanClass)) {
            scanner.setRetrofitFactoryBean(BeanUtils.instantiateClass(mapperFactoryBeanClass));
        }

        List<String> basePackages = new ArrayList<>();
        for (String pkg : annotationAttributes.getStringArray("value")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : annotationAttributes.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : annotationAttributes.getClassArray("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

}

