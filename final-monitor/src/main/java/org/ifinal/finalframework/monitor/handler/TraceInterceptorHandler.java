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
 *
 */

package org.ifinal.finalframework.monitor.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.monitor.context.TraceContext;
import org.ifinal.finalframework.monitor.trace.Tracer;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class TraceInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements
    InterceptorHandler<Tracer, AnnotationAttributes> {

    private static final String TRACE_CONTEXT = "traceContext";

    @Override
    public Object before(final Tracer executor, final InvocationContext context,
        final AnnotationAttributes annotation) {

        TraceContext traceContext = new TraceContext();
        traceContext.setTrace(annotation.getString("trace"));
        context.addAttribute(TRACE_CONTEXT, traceContext);
        executor.start(traceContext);
        return null;
    }

    @Override
    public void after(final Tracer executor, final InvocationContext context, final AnnotationAttributes annotation,
        final Object result,
        final Throwable throwable) {

        executor.stop(context.getAttribute(TRACE_CONTEXT));
    }

}
