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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.core.aop.JoinPoint;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface JoinPointInterceptorHandler<E, A> extends InterceptorHandler<E, A> {

    @Nullable
    JoinPoint point(A annotation);

    @Override
    default Object before(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation) {

        if (JoinPoint.BEFORE == point(annotation)) {
            handle(executor, context, annotation, null, null);
        }
        return null;
    }

    @Override
    default void afterReturning(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation,
        Object result) {

        if (JoinPoint.AFTER_RETURNING == point(annotation)) {
            handle(executor, context, annotation, result, null);
        }
    }

    @Override
    default void afterThrowing(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation,
        @NonNull Throwable throwable) {

        if (JoinPoint.AFTER_THROWING == point(annotation)) {
            handle(executor, context, annotation, null, throwable);
        }
    }

    @Override
    default void after(@NonNull E executor, @NonNull InvocationContext context, @NonNull A annotation, Object result,
        Throwable throwable) {

        if (JoinPoint.AFTER == point(annotation)) {
            handle(executor, context, annotation, result, throwable);
        }
    }

}
