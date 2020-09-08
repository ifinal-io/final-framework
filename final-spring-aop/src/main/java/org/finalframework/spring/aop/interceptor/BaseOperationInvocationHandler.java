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

package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Asserts;
import org.finalframework.spring.aop.*;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:32:11
 * @since 1.0
 */
public class BaseOperationInvocationHandler implements OperationInvocationHandler {

    @NonNull
    private final OperationConfiguration configuration;

    public BaseOperationInvocationHandler(@NonNull OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Object handleBefore(Collection<OperationContext<Operation>> contexts) {
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            final Object cacheValue = handler.before(executor, context);
            if (cacheValue != null) {
                return cacheValue;
            }
        }
        return null;
    }

    @Override
    public void handleAfterReturning(Collection<OperationContext<Operation>> contexts, Object result) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterReturning(executor, context, result);
        }
    }

    @Override
    public void handleAfterThrowing(Collection<OperationContext<Operation>> contexts, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.afterThrowing(executor, context, throwable);
        }
    }

    @Override
    public void handleAfter(Collection<OperationContext<Operation>> contexts, Object result, Throwable throwable) {
        if (Asserts.isEmpty(contexts)) {
            return;
        }
        for (OperationContext<? extends Operation> context : contexts) {
            final OperationHandler handler = configuration.getHandler(context.operation().handler());
            final Executor executor = configuration.getExecutor(context.operation());
            handler.after(executor, context, result, throwable);
        }
    }
}
