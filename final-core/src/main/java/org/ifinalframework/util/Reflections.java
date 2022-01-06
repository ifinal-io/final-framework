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

package org.ifinalframework.util;

import org.ifinalframework.json.Json;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Reflections.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class Reflections {

    private static final MethodFinder METHOD_FINDER = new DefaultMethodFinder();
    private static final MethodInvoker METHOD_INVOKER = new DefaultMethodInvoker();

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
    public static Method findMethod(@NonNull Class<?> clazz, @NonNull String methodName,
                                    @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {
        return METHOD_FINDER.find(clazz, methodName, parameterTypes, arguments);
    }

    @Nullable
    public static Method findMethod(final @NonNull Class<?> clazz, final @NonNull String name,
                                    final Class<?>... paramTypes) {
        return findMethod(clazz, name, paramTypes, null);
    }

    @Nullable
    public static Method findMethod(final @NonNull Class<?> clazz, final @NonNull String name) {
        return findMethod(clazz, name, null, null);
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

    public static Object invokeMethod(@NonNull Object target, @NonNull String method, @Nullable Class<?>[] parameterTypes, @Nullable Object... args) {
        Method targetMethod = findMethod(AopUtils.getTargetClass(target), method, parameterTypes, args);
        ReflectionUtils.makeAccessible(targetMethod);
        return invokeMethod(targetMethod, target, args);
    }

    public static Object invokeMethod(@NonNull Method method, @NonNull Object target, @Nullable Object... args) {
        return METHOD_INVOKER.invoke(method, target, args);
    }

    /**
     * @author likly
     * @version 1.2.4
     **/
    @FunctionalInterface
    public static interface MethodFinder {
        @Nullable
        Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                    @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments);
    }

    /**
     * @author likly
     * @version 1.2.4
     **/
    public static class MethodFinders implements MethodFinder {

        private final List<MethodFinder> methodFinders;

        public MethodFinders(List<MethodFinder> methodFinders) {
            this.methodFinders = methodFinders;
        }


        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.nonNull(parameterTypes) && Objects.nonNull(arguments) && !Objects.equals(parameterTypes.length, arguments.length)) {
                throw new IllegalArgumentException(String.format("%s.%s parameterTypes and arguments length not matched.", targetClass.getCanonicalName(), methodName));
            }

            return methodFinders.stream()
                    .map(it -> it.find(targetClass, methodName, parameterTypes, arguments))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);

        }


    }

    /**
     * @author likly
     * @version 1.2.4
     **/
    static class DefaultMethodFinder extends MethodFinders {
        public DefaultMethodFinder() {
            super(Arrays.asList(
                    new ParameterTypesMethodFinder(),
                    new NameMethodFinder(),
                    new ArgumentsMethodFinder()
            ));
        }
    }

    private static class ParameterTypesMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.isNull(parameterTypes)) {
                return ReflectionUtils.findMethod(targetClass, methodName);
            }

            return ReflectionUtils.findMethod(targetClass, methodName, parameterTypes);
        }
    }

    private static class ArgumentsMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.isNull(arguments)) {
                return null;
            }
            Method[] candidates = ReflectionUtils.getAllDeclaredMethods(targetClass);
            int argCount = arguments.length;

            if (isAllString(arguments)) {
                List<Method> matchingMethods = Arrays.stream(candidates)
                        .filter(it -> it.getName().equals(methodName) && it.getParameterCount() == argCount)
                        .collect(Collectors.toList());

                if (matchingMethods.size() == 1) {
                    return matchingMethods.get(0);
                }

                if (matchingMethods.size() > 1) {
                    throw new IllegalStateException("Found not only one method of " + methodName + " in class " + targetClass.getCanonicalName());
                }

                return null;
            } else {
                int minTypeDiffWeight = Integer.MAX_VALUE;
                Method matchingMethod = null;

                for (Method candidate : candidates) {
                    if (candidate.getName().equals(methodName)) {
                        if (candidate.getParameterCount() == argCount) {
                            Class<?>[] paramTypes = candidate.getParameterTypes();
                            int typeDiffWeight = org.springframework.util.MethodInvoker.getTypeDifferenceWeight(paramTypes, arguments);
                            if (typeDiffWeight < minTypeDiffWeight) {
                                minTypeDiffWeight = typeDiffWeight;
                                matchingMethod = candidate;
                            }
                        }
                    }
                }

                return matchingMethod;
            }

        }

        private boolean isAllString(Object[] arguments) {
            for (Object argument : arguments) {
                if (!(argument instanceof String)) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class NameMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.nonNull(arguments)) {
                return null;
            }

            Method[] candidates = ReflectionUtils.getAllDeclaredMethods(targetClass);

            List<Method> matchingMethods = Arrays.stream(candidates)
                    .filter(it -> it.getName().equals(methodName))
                    .collect(Collectors.toList());

            if (matchingMethods.size() == 1) {
                return matchingMethods.get(0);
            }

            if (matchingMethods.size() > 1) {
                throw new IllegalStateException("Found not only one method of " + methodName + " in class " + targetClass.getCanonicalName());
            }

            return null;
        }
    }


    /**
     * @author likly
     * @version 1.2.4
     **/
    static class DefaultMethodInvoker implements MethodInvoker {
        @Override
        @Nullable
        public Object invoke(@NonNull Method method, @NonNull Object target, @Nullable Object... args) {

            if (Objects.isNull(args) || args.length == 0) {
                return ReflectionUtils.invokeMethod(method, target);
            }

            Object[] arguments = new Object[args.length];
            Type[] types = method.getGenericParameterTypes();
            for (int i = 0; i < arguments.length; i++) {
                Object arg = args[i];
                if (arg instanceof String) {
                    arguments[i] = Json.toObject((String) arg, types[i]);
                } else {
                    arguments[i] = arg;
                }
            }
            return ReflectionUtils.invokeMethod(method, target, arguments);

        }
    }

    /**
     * @author likly
     * @version 1.2.4
     **/
    @FunctionalInterface
    public static interface MethodInvoker {
        @Nullable
        Object invoke(@NonNull Method method, @NonNull Object target, @Nullable Object... args);
    }
}
