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

package org.ifinalframework.aop.single;

import org.springframework.lang.Nullable;

import org.ifinalframework.aop.AnnotationBuilder;
import org.ifinalframework.aop.AnnotationFinder;
import org.ifinalframework.aop.AnnotationParser;
import org.ifinalframework.aop.AnnotationSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleAnnotationSource<A extends Annotation, E> implements AnnotationSource<Collection<E>> {

    private final Map<Object, Collection<E>> cache = new ConcurrentHashMap<>(1024);

    private final AnnotationParser<Collection<E>> parser;

    public SingleAnnotationSource(final Class<A> annotationType, final AnnotationBuilder<A, E> builder) {

        this(new SingleAnnotationParser<>(annotationType, builder));
    }

    public SingleAnnotationSource(final AnnotationFinder<Collection<A>> finder, final AnnotationBuilder<A, E> builder) {

        this(new SingleAnnotationParser<>(finder, builder));
    }

    public SingleAnnotationSource(final AnnotationParser<Collection<E>> parser) {

        this.parser = parser;
    }

    @Override
    public Collection<E> getAnnotations(final Method method, final Class<?> targetClass) {

        if (method.getDeclaringClass() == Object.class) {
            return Collections.emptyList();
        }

        final Object cacheKey = getCacheKey(method, targetClass);
        return this.cache.computeIfAbsent(cacheKey, key -> computeAnnotations(method, targetClass));
    }

    private Collection<E> computeAnnotations(final Method method, final @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return Collections.emptyList();
        }

        final List<E> annotations = new ArrayList<>();

        annotations.addAll(parser.parseAnnotations(targetClass));
        annotations.addAll(parser.parseAnnotations(method));

        final Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            annotations.addAll(parser.parseAnnotations(parameters[i], i));
        }

        return annotations;
    }

    protected boolean allowPublicMethodsOnly() {
        return false;
    }

}
