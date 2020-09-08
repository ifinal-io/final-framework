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

package org.finalframework.context.exception.result;

import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.context.exception.InvokeExceptionWrapper;
import org.finalframework.context.exception.handler.GlobalExceptionHandler;
import org.finalframework.core.Asserts;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-15 14:00:27
 * @since 1.0
 */
public abstract class ResultInvokeExceptionWrapper<T extends Serializable> implements InvokeExceptionWrapper<Result<T>> {

    private GlobalExceptionHandler<Result<?>> globalExceptionHandler;

    public ResultInvokeExceptionWrapper(GlobalExceptionHandler<Result<?>> globalExceptionHandler) {
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public GlobalExceptionHandler<Result<?>> getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final Result<T> invoke() {
        String trace = MDC.get("trace");
        if (Asserts.isBlank(trace)) {
            trace = UUID.randomUUID().toString();
            MDC.put("trace", trace);
        }
        InvokeContext context = new InvokeContext();
        context.setTrace(trace);
        try {
            final T data = handle();
            final Result<T> result = R.success(data);
            result.setTrace(trace);
            result.setTimestamp(System.currentTimeMillis());
            return result;
        } catch (Throwable throwable) {
            if (globalExceptionHandler != null) {
                return (Result<T>) globalExceptionHandler.handle(throwable);
            }
            final Result<?> result = R.failure(500, "服务异常");
            result.setTrace(trace);
            result.setTimestamp(System.currentTimeMillis());
            return (Result<T>) result;
        }
    }

    protected abstract T handle() throws Throwable;

}
