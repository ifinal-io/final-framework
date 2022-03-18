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

import org.ifinalframework.aop.AnnotationSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationSource<E> implements AnnotationSource<Map<Class<? extends Annotation>, Collection<E>>> {

    private final Map<Object, Map<Class<? extends Annotation>, Collection<E>>> cache = new ConcurrentHashMap<>(1024);

    private final Map<Class<? extends Annotation>, AnnotationSource<Collection<E>>> sourceMap = new LinkedHashMap<>();

    public void addAnnotationSource(final Class<? extends Annotation> annotationType,
        final AnnotationSource<Collection<E>> source) {

        sourceMap.put(annotationType, source);
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<E>> getAnnotations(final Method method,
        final Class<?> targetClass) {

        final Object cacheKey = getCacheKey(method, targetClass);

        return cache.computeIfAbsent(cacheKey, key -> {
            final Map<Class<? extends Annotation>, Collection<E>> map = new HashMap<>();

            for (Map.Entry<Class<? extends Annotation>, AnnotationSource<Collection<E>>> entry : sourceMap.entrySet()) {

                final Collection<E> annotations = entry.getValue().getAnnotations(method, targetClass);

                if (Objects.isNull(annotations) || annotations.isEmpty()) {
                    continue;
                }

                map.put(entry.getKey(), annotations);

            }

            return map;
        });

    }

}
