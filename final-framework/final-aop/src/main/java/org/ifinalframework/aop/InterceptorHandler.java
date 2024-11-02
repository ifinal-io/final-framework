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

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface InterceptorHandler<E, A> {

    default Object before(final @NonNull E executor, final @NonNull InvocationContext context,
                          final @NonNull A annotation) {

        handle(executor, context, annotation, null, null);
        return null;
    }

    default void afterReturning(final @NonNull E executor, final @NonNull InvocationContext context,
                                final @NonNull A annotation, final @Nullable Object result) {

        handle(executor, context, annotation, result, null);
    }

    default void handle(final @NonNull E executor, final @NonNull InvocationContext context,
                        final @NonNull A annotation, final @Nullable Object result, final @Nullable Throwable throwable) {

    }

    default void afterThrowing(final @NonNull E executor, final @NonNull InvocationContext context,
                               final @NonNull A annotation, final @NonNull Throwable throwable) {

        handle(executor, context, annotation, null, throwable);
    }

    default void after(final @NonNull E executor, final @NonNull InvocationContext context,
                       final @NonNull A annotation, final @Nullable Object result, final @Nullable Throwable throwable) {

        handle(executor, context, annotation, result, throwable);
    }

}
