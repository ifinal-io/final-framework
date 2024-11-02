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

package org.ifinalframework.data.util;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import org.ifinalframework.util.Asserts;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Beans.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Beans {

    /**
     * return the beans annotated by specified annotation.
     *
     * @param applicationContext spring application context
     * @param annotationType     the specified annotation
     * @param <T>                the target beans type
     * @return list
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> findBeansByAnnotation(final ApplicationContext applicationContext,
                                             final Class<? extends Annotation> annotationType) {

        return findAllBeansAnnotatedBy(applicationContext, annotationType)
                .map(it -> (T) it)
                .collect(Collectors.toList());
    }

    static Stream<String> findAllBeans(final ApplicationContext applicationContext) {

        Asserts.requiredNonNull(applicationContext, "applicationContext must be not null!");
        return Arrays.stream(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class));
    }

    static Stream<Object> findAllBeansAnnotatedBy(final ApplicationContext applicationContext,
                                                  final Class<? extends Annotation> annotationType) {

        Asserts.requiredNonNull(applicationContext, "applicationContext must be not null!");
        Asserts.requiredNonNull(annotationType, "annotationType must be not null!");
        return findAllBeans(applicationContext)
                .filter(name -> applicationContext.findAnnotationOnBean(name, annotationType) != null)
                .map(applicationContext::getBean);
    }

}
