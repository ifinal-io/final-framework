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

import org.springframework.lang.NonNull;

import org.ifinalframework.aop.InterceptorHandler;
import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.aop.MethodInvocationDispatcher;

import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleMethodInvocationDispatcher<T> implements MethodInvocationDispatcher<Boolean> {

    private final List<InterceptorHandler<T, Boolean>> handlers;

    protected SimpleMethodInvocationDispatcher(final List<InterceptorHandler<T, Boolean>> handlers) {

        this.handlers = handlers;
    }

    @Override
    public Object before(final @NonNull InvocationContext context, final @NonNull Boolean annotations) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            final Object value = handler.before(executor, context, annotations);
            if (Objects.nonNull(value)) {
                return value;
            }
        }

        return null;
    }

    @Override
    public void afterReturning(final @NonNull InvocationContext context, final @NonNull Boolean annotations,
        final Object result) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.afterReturning(executor, context, annotations, result);
        }
    }

    @Override
    public void afterThrowing(final @NonNull InvocationContext context, final @NonNull Boolean annotations,
        final Throwable throwable) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.afterThrowing(executor, context, annotations, throwable);
        }
    }

    @Override
    public void after(final @NonNull InvocationContext context, final @NonNull Boolean annotations, final Object result,
        final Throwable throwable) {

        for (InterceptorHandler<T, Boolean> handler : handlers) {
            final T executor = getExecutor();
            handler.after(executor, context, annotations, result, throwable);
        }
    }

    @NonNull
    protected abstract T getExecutor();

}
