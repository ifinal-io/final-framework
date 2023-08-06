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

package org.ifinalframework.aop;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.expression.AnnotatedElementKey;

import org.ifinalframework.context.expression.MethodMetadata;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultAnnotationMethodInterceptor<A> implements AnnotationMethodInterceptor<A> {

    private final Map<AnnotatedElementKey, MethodMetadata> metadataCache = new ConcurrentHashMap<>(1024);

    private final AnnotationSource<A> source;

    private final MethodInvocationDispatcher<A> dispatcher;

    public DefaultAnnotationMethodInterceptor(final AnnotationSource<A> source,
                                              final MethodInvocationDispatcher<A> dispatcher) {

        this.source = source;
        this.dispatcher = dispatcher;
    }

    @Override
    public A findAnnotations(final Method method, final Class<?> clazz) {

        return source.getAnnotations(method, clazz);
    }

    @Override
    public Object invoke(final MethodInvocation invocation, final A annotations) throws Throwable {

        final Class<?> targetClass = getTargetClass(invocation.getThis());
        final MethodMetadata metadata = getOperationMetadata(invocation.getMethod(), targetClass);

        final InvocationContext context = new DefaultInvocationContext(metadata, invocation.getThis(),
                invocation.getArguments());

        final Object operationValue = dispatcher.before(context, annotations);
        if (Objects.nonNull(operationValue)) {
            return operationValue;
        }

        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invocation.proceed();
        } catch (Throwable e) {
            throwable = e;
        }

        if (throwable == null) {
            dispatcher.afterReturning(context, annotations, returnValue);
        } else {
            dispatcher.afterThrowing(context, annotations, throwable);
        }

        dispatcher.after(context, annotations, returnValue, throwable);

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;
    }

    private MethodMetadata getOperationMetadata(final Method method, final Class<?> targetClass) {

        final AnnotatedElementKey cacheKey = new AnnotatedElementKey(method, targetClass);
        return this.metadataCache.computeIfAbsent(cacheKey, key -> new MethodMetadata(method, targetClass));
    }

    private Class<?> getTargetClass(final Object target) {

        return AopProxyUtils.ultimateTargetClass(target);
    }

}
