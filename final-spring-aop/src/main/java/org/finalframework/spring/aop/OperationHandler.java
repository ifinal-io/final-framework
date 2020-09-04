/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.aop;

import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * {@link Operation}处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-26 09:42:44
 * @since 1.0
 */
public interface OperationHandler<E extends Executor, O extends Operation> {

    /**
     * 在方法执行之前，执行{@link Operation}所描述操作。如果些方法的返回值不为{@code null}，则该返回值将会作为目标方法的返回值直接返回，
     * 从而实现类似缓存的功能。
     *
     * @param executor {@link Operation}的执行者
     * @param context  {@link Operation}的上下文
     * @see CutPoint#BEFORE
     */
    default Object before(@NonNull E executor, @NonNull OperationContext<O> context) {
        return null;
    }

    /**
     * 在目标方法{@link Method}的生命周期{@link CutPoint#AFTER_RETURNING}后，作进一步的处理。如设置缓存，记录日志等。
     *
     * @param executor {@link Operation}的执行者
     * @param context  {@link Operation}的上下文
     * @param result   目标方法{@link Method}的返回值，可能为{@code null}
     * @see #after(Executor, OperationContext, Object, Throwable)
     */
    default void afterReturning(@NonNull E executor, @NonNull OperationContext<O> context, @Nullable Object result) {
    }

    /**
     * 在目标方法{@link Method}的生命周期{@link CutPoint#AFTER_THROWING}后，作进一步的处理。如记录失败次数，记录日志等。
     *
     * @param executor  {@link Operation}的执行者
     * @param context   {@link Operation}的上下文
     * @param throwable 目标方法{@link Method}的抛出的异常
     * @see #after(Executor, OperationContext, Object, Throwable)
     */
    default void afterThrowing(@NonNull E executor, @NonNull OperationContext<O> context, @NonNull Throwable throwable) {
    }

    /**
     * 在目标方法{@link Method}的生命周期{@link CutPoint#AFTER}后，作进一步的处理。如设置缓存，记录日志等。
     *
     * @param executor  {@link Operation}的执行者
     * @param context   {@link Operation}的上下文
     * @param result    目标方法{@link Method}的返回值，可能为{@code null}
     * @param throwable 目标方法{@link Method}的抛出的异常,可能为{@code null}
     * @see #afterReturning(Executor, OperationContext, Object)
     * @see #afterThrowing(Executor, OperationContext, Throwable)
     */
    default void after(@NonNull E executor, @NonNull OperationContext<O> context, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
