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
 *
 */

package org.ifinal.finalframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

/**
 * Reflections.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class Reflections {

    private Reflections() {
    }

    @Nullable
    public static Field findField(final @NonNull Class<?> clazz, final @NonNull String name) {
        return ReflectionUtils.findField(clazz, name);
    }

    @NonNull
    public static Field findRequiredField(final @NonNull Class<?> clazz, final @NonNull String name) {
        final Field field = findField(clazz, name);
        Objects.requireNonNull(field, String.format("can not find field of name '%s' on class '%s'", name, clazz));
        return field;
    }

    @Nullable
    public static Method findMethod(final @NonNull Class<?> clazz, final @NonNull String name,
        final Class<?>... paramTypes) {
        return ReflectionUtils.findMethod(clazz, name, paramTypes);
    }

    @Nullable
    public static Method findMethod(final @NonNull Class<?> clazz, final @NonNull String name) {
        return ReflectionUtils.findMethod(clazz, name);
    }

    @NonNull
    public static Method findRequiredMethod(final @NonNull Class<?> clazz, final @NonNull String name,
        final Class<?>... paramTypes) {
        final Method method = findMethod(clazz, name, paramTypes);
        Objects.requireNonNull(method,
            String.format("can not find required method of name '%s' on class '%s'", name, clazz));
        return method;
    }

    @NonNull
    public static Method findRequiredMethod(final @NonNull Class<?> clazz, final @NonNull String name) {
        final Method method = findMethod(clazz, name);
        Objects.requireNonNull(method,
            String.format("can not find required method of name '%s' on class '%s'", name, clazz));
        return method;
    }

    @NonNull
    public static AnnotationAttributes getAnnotationAttributes(final @NonNull Annotation annotation) {

        return AnnotationUtils.getAnnotationAttributes(null, annotation);
    }

    @Nullable
    public static AnnotationAttributes findAnnotationAttributes(final AnnotatedElement ae,
        final Class<? extends Annotation> annotationType) {
        final Annotation annotation = AnnotationUtils.findAnnotation(ae, annotationType);
        if (Objects.isNull(annotation)) {
            return null;
        }
        return AnnotationUtils.getAnnotationAttributes(ae, annotation);
    }

    public static Class findParameterizedClassArgumentClass(final @NonNull Class<?> target,
        final @NonNull Class<?> targetInterface, final int index) {
        final Type type = target.getGenericSuperclass();
        if (type instanceof ParameterizedType
            && targetInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[index];
        }
        throw new IllegalArgumentException("");
    }

    public static Class findParameterizedInterfaceArgumentClass(final @NonNull Class<?> target,
        final @NonNull Class<?> targetInterface, final int index) {

        final Type[] genericInterfaces = target.getGenericInterfaces();

        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType
                && targetInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return (Class) ((ParameterizedType) type).getActualTypeArguments()[index];
            }
        }

        throw new IllegalArgumentException("");
    }

}
