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

package org.finalframework.monitor.handler;

import org.finalframework.monitor.context.TraceContext;
import org.finalframework.monitor.executor.Tracer;
import org.finalframework.monitor.operation.TraceOperation;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-09 17:42
 * @since 1.0
 */
@SpringComponent
public class TraceOperationHandler implements OperationHandler<Tracer, TraceOperation> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    public Object before(Tracer executor, OperationContext<TraceOperation> context) {

        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(context.operation().trace());
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }

    @Override
    public void afterReturning(Tracer executor, OperationContext<TraceOperation> context, Object result) {

    }

    @Override
    public void afterThrowing(Tracer executor, OperationContext<TraceOperation> context, Throwable throwable) {

    }

    @Override
    public void after(Tracer executor, OperationContext<TraceOperation> context, Object result, Throwable throwable) {
        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }
}
