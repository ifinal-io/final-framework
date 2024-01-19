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

package org.ifinalframework.aop.simple;

import org.ifinalframework.aop.AnnotationSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleAnnotationSource implements AnnotationSource<Boolean> {

    private final Map<Object, Boolean> cache = new ConcurrentHashMap<>(1024);

    private final SimpleAnnotationFinder finder;

    public SimpleAnnotationSource(final Collection<Class<? extends Annotation>> annotationTypes) {

        this.finder = new SimpleAnnotationFinder(annotationTypes);
    }

    @Override
    public Boolean getAnnotations(final Method method, final Class<?> targetClass) {

        final Object cacheKey = getCacheKey(method, targetClass);
        return cache.computeIfAbsent(cacheKey, key -> {

            if (Boolean.TRUE.equals(finder.findAnnotations(method))) {
                return true;
            }

            if (Boolean.TRUE.equals(finder.findAnnotations(targetClass))) {
                return true;
            }

            for (Parameter parameter : method.getParameters()) {
                if (Boolean.TRUE.equals(finder.findAnnotations(parameter))) {
                    return true;
                }
            }

            return false;
        });
    }

}
