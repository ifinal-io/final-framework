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
import org.springframework.util.MultiValueMap;

import org.ifinalframework.aop.InterceptorHandler;
import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.aop.MethodInvocationDispatcher;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class MultiMethodInvocationDispatcher<E, A> implements
    MethodInvocationDispatcher<Map<Class<? extends Annotation>, Collection<A>>> {

    private final MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers;

    public MultiMethodInvocationDispatcher(
        final MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers) {

        this.handlers = handlers;
    }

    @Override
    public Object before(final @NonNull InvocationContext context,
        final @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations) {

        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            final List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                final Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }

                for (A annotation : as) {
                    final E executor = getExecutor(annotation);
                    final Object value = handler.before(executor, context, annotation);
                    if (Objects.nonNull(value)) {
                        return value;
                    }
                }

            }
        }

        return null;
    }

    @Override
    public void afterReturning(final @NonNull InvocationContext context,
        final @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations,
        final Object result) {

        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            final List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                final Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    final E executor = getExecutor(annotation);
                    handler.afterReturning(executor, context, annotation, result);
                }

            }
        }
    }

    @Override
    public void afterThrowing(final @NonNull InvocationContext context,
        final @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations,
        final @NonNull Throwable throwable) {

        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            final List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                final Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    final E executor = getExecutor(annotation);
                    handler.afterThrowing(executor, context, annotation, throwable);
                }

            }
        }
    }

    @Override
    public void after(final @NonNull InvocationContext context,
        final @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations, final Object result,
        final Throwable throwable) {

        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            final List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                final Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    final E executor = getExecutor(annotation);
                    handler.after(executor, context, annotation, result, throwable);
                }

            }
        }
    }

    @NonNull
    protected abstract E getExecutor(A annotation);

}
