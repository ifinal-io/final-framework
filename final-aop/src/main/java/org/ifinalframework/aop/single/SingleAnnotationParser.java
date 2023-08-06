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

import org.ifinalframework.aop.AnnotationBuilder;
import org.ifinalframework.aop.AnnotationFinder;
import org.ifinalframework.aop.AnnotationParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SingleAnnotationParser<A extends Annotation, E> implements AnnotationParser<Collection<E>> {

    private final AnnotationFinder<Collection<A>> finder;

    private final AnnotationBuilder<A, E> builder;

    public SingleAnnotationParser(final Class<A> annotationType, final AnnotationBuilder<A, E> builder) {

        this(new SingleAnnotationFinder<>(annotationType), builder);
    }

    public SingleAnnotationParser(final AnnotationFinder<Collection<A>> finder, final AnnotationBuilder<A, E> builder) {

        this.finder = finder;
        this.builder = builder;
    }

    @Override
    public Collection<E> parseAnnotations(final Class<?> clazz) {

        return finder.findAnnotations(clazz)
                .stream()
                .map(annotation -> builder.build(clazz, annotation))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<E> parseAnnotations(final Method method) {

        return finder.findAnnotations(method)
                .stream()
                .map(annotation -> builder.build(method, annotation))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<E> parseAnnotations(final Parameter parameter, final Integer index) {

        return finder.findAnnotations(parameter)
                .stream()
                .map(annotation -> builder.build(parameter, index, annotation))
                .collect(Collectors.toList());
    }

}
