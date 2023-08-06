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

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author ilikly
 * @version 1.0.0
 * @see MethodInvocation#proceed()
 * @since 1.0.0
 */
public interface MethodInvocationDispatcher<A> {

    @Nullable
    Object before(@NonNull InvocationContext context, @NonNull A annotations);

    default void afterReturning(@NonNull InvocationContext context, @NonNull A annotations, @Nullable Object result) {

    }

    default void afterThrowing(@NonNull InvocationContext context, @NonNull A annotations,
                               @NonNull Throwable throwable) {

    }

    default void after(@NonNull InvocationContext context, @NonNull A annotations, @Nullable Object result,
                       @Nullable Throwable throwable) {

    }

}
