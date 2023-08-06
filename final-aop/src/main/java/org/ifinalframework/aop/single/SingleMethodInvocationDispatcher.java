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

import org.springframework.lang.NonNull;

import org.ifinalframework.aop.InterceptorHandler;
import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.aop.MethodInvocationDispatcher;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SingleMethodInvocationDispatcher<E, A> implements MethodInvocationDispatcher<Collection<A>> {

    private List<InterceptorHandler<E, A>> handlers;

    protected SingleMethodInvocationDispatcher(final List<InterceptorHandler<E, A>> handlers) {

        this.handlers = handlers;
    }

    @Override
    public Object before(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                final E handlerExecutor = getExecutor(annotation);
                final Object value = handler.before(handlerExecutor, context, annotation);
                if (Objects.nonNull(value)) {
                    return value;
                }
            }
        }

        return null;
    }

    @Override
    public void afterReturning(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations,
                               final Object result) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                final E handlerExecutor = getExecutor(annotation);
                handler.afterReturning(handlerExecutor, context, annotation, result);
            }
        }
    }

    @Override
    public void afterThrowing(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations,
                              final @NonNull Throwable throwable) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                final E handlerExecutor = getExecutor(annotation);
                handler.afterThrowing(handlerExecutor, context, annotation, throwable);
            }
        }
    }

    @Override
    public void after(final @NonNull InvocationContext context, final @NonNull Collection<A> annotations,
                      final Object result, final Throwable throwable) {

        for (InterceptorHandler<E, A> handler : handlers) {
            for (A annotation : annotations) {
                final E handlerExecutor = getExecutor(annotation);
                handler.after(handlerExecutor, context, annotation, result, throwable);
            }
        }
    }

    @NonNull
    protected abstract E getExecutor(A annotation);

}
