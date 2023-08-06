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

package org.ifinalframework.context.resource;

import org.springframework.core.annotation.AnnotatedElementUtils;

import org.ifinalframework.context.annotation.ResourceValue;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ResourceValueUtils.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ResourceValueUtils {

    private ResourceValueUtils() {
    }

    public static Collection<ResourceValueHolder> findAllResourceValueHolders(final Object target, final Class<?> clazz) {

        final Collection<ResourceValueHolder> holders = new LinkedList<>();

        if (Objects.isNull(clazz)) {
            return holders;
        }

        ResourceValue resourceValue = AnnotatedElementUtils.findMergedAnnotation(clazz, ResourceValue.class);

        if (Objects.isNull(resourceValue)) {
            return holders;
        }

        if (Objects.equals(Object.class, clazz)) {
            return holders;
        }

        for (final Field field : clazz.getDeclaredFields()) {

            ResourceValueHolder valueHolder = processElement(target, field, resourceValue);

            if (Objects.nonNull(valueHolder)) {
                holders.add(valueHolder);
            }

        }

        for (final Method method : clazz.getDeclaredMethods()) {
            ResourceValueHolder valueHolder = processElement(target, method, resourceValue);

            if (Objects.nonNull(valueHolder)) {
                holders.add(valueHolder);
            }
        }

        holders.addAll(findAllResourceValueHolders(target, clazz.getSuperclass()));

        for (final Class<?> item : clazz.getInterfaces()) {
            holders.addAll(findAllResourceValueHolders(target, item));
        }

        return holders;

    }

    private static ResourceValueHolder processElement(final Object target, final AnnotatedElement element,
                                                      final ResourceValue resourceValue) {
        ResourceValue annotation = AnnotatedElementUtils.findMergedAnnotation(element, ResourceValue.class);

        if (Objects.isNull(annotation)) {
            return null;
        }

        return new ResourceValueHolder(key(resourceValue, annotation), target, element);

    }

    private static String key(final ResourceValue... resourceValues) {
        return Arrays.stream(resourceValues)
                .map(ResourceValue::value)
                .filter(it -> !it.isEmpty())
                .collect(Collectors.joining("."));
    }

}
