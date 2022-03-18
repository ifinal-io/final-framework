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

package org.ifinalframework.aop.multi;

import org.springframework.lang.NonNull;

import org.ifinalframework.aop.AnnotationFinder;
import org.ifinalframework.aop.single.SingleAnnotationFinder;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationFinder implements
    AnnotationFinder<Map<Class<? extends Annotation>, Collection<? extends Annotation>>>, Serializable {

    private static final long serialVersionUID = -8088506337000616189L;

    private final Collection<Class<? extends Annotation>> annotationTypes;

    private final Map<Class<? extends Annotation>, SingleAnnotationFinder<? extends Annotation>> finders
        = new LinkedHashMap<>();

    public MultiAnnotationFinder(final Collection<Class<? extends Annotation>> annotationTypes) {

        this.annotationTypes = annotationTypes;
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<? extends Annotation>> findAnnotations(
        final @NonNull AnnotatedElement ae) {

        final Map<Class<? extends Annotation>, Collection<? extends Annotation>> map = new LinkedHashMap<>();

        for (Class<? extends Annotation> annotationType : annotationTypes) {
            final Collection<? extends Annotation> annotations
                = getAnnotationFinder(annotationType).findAnnotations(ae);
            if (annotations.isEmpty()) {
                continue;
            }

            map.put(annotationType, annotations);
        }

        return map;
    }

    private SingleAnnotationFinder<? extends Annotation> getAnnotationFinder(
        final Class<? extends Annotation> annotationType) {

        return finders.computeIfAbsent(annotationType, SingleAnnotationFinder::new);
    }

}
