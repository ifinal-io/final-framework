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

package org.finalframework.data.util;

import org.finalframework.core.Asserts;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public interface Beans {

    /**
     * return the beans annotated by specified annotation.
     *
     * @param applicationContext spring application context
     * @param annotationType     the specified annotation
     * @param <T>                the target beans type
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> findBeansByAnnotation(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        return findAllBeansAnnotatedBy(applicationContext, annotationType)
                .map(it -> (T) it)
                .collect(Collectors.toList());
    }

    static Stream<String> findAllBeans(ApplicationContext applicationContext) {
        Asserts.isNull(applicationContext, "applicationContext must be not null!");
        return Arrays.stream(BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class));
    }

    static Stream<Object> findAllBeansAnnotatedBy(ApplicationContext applicationContext, Class<? extends Annotation> annotationType) {
        Asserts.isNull(applicationContext, "applicationContext must be not null!");
        Asserts.isNull(annotationType, "annotationType must be not null!");
        return findAllBeans(applicationContext).filter(name -> applicationContext.findAnnotationOnBean(name, annotationType) != null)
                .map(applicationContext::getBean);
    }


}
