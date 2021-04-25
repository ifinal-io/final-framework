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
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.JoinPointInterceptorHandler;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.core.aop.JoinPoint;
import org.ifinal.finalframework.monitor.action.Action;
import org.ifinal.finalframework.monitor.action.Recorder;
import org.ifinal.finalframework.monitor.annotation.ActionMonitor;

import org.slf4j.MDC;

/**
 * @author likly
 * @version 1.0.0
 * @see ActionMonitor
 * @since 1.0.0
 */
public class ActionInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements
    JoinPointInterceptorHandler<Recorder, AnnotationAttributes> {

    /**
     * @see ActionMonitor#name()
     * @see ActionMonitor#value()
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * @see ActionMonitor#target()
     */
    private static final String ATTRIBUTE_TARGET = "target";

    /**
     * @see ActionMonitor#code()
     */
    private static final String ATTRIBUTE_CODE = "code";

    /**
     * @see ActionMonitor#type()
     */
    private static final String ATTRIBUTE_TYPE = "type";

    /**
     * @see ActionMonitor#level()
     */
    private static final String ATTRIBUTE_LEVEL = "level";

    /**
     * @see ActionMonitor#point()
     */
    private static final String ATTRIBUTE_POINT = "point";

    /**
     * @param annotation annotation
     * @return the action join point
     * @see ActionMonitor#point()
     */
    @Override
    public JoinPoint point(final AnnotationAttributes annotation) {

        return annotation.getEnum(ATTRIBUTE_POINT);
    }

    @Override
    public void handle(final @NonNull Recorder executor, final @NonNull InvocationContext context,
        final @NonNull AnnotationAttributes annotation,
        final Object result, final Throwable throwable) {

        EvaluationContext evaluationContext = createEvaluationContext(context, result, throwable);
        MethodMetadata metadata = context.metadata();

        final Action action = new Action();
        action.setName(generateName(annotation.getStringArray(ATTRIBUTE_NAME), ":", metadata, evaluationContext));
        action.setTarget(generateTarget(annotation.getString(ATTRIBUTE_TARGET), metadata, evaluationContext));
        action.setCode(annotation.getString(ATTRIBUTE_CODE));
        action.setType(annotation.getString(ATTRIBUTE_TYPE));
        action.setLevel(annotation.getEnum(ATTRIBUTE_LEVEL));
        action.setException(throwable);
        action.setTrace(MDC.get("trace"));
        action.setTimestamp(System.currentTimeMillis());

        executor.record(action);
    }

}
