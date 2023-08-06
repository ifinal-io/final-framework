/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.monitor.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import org.ifinalframework.aop.InvocationContext;
import org.ifinalframework.aop.JoinPointInterceptorHandler;
import org.ifinalframework.context.expression.MethodMetadata;
import org.ifinalframework.core.aop.JoinPoint;
import org.ifinalframework.monitor.action.Action;
import org.ifinalframework.monitor.action.OperationActionHandler;
import org.ifinalframework.monitor.annotation.OperationAction;

import org.slf4j.MDC;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author ilikly
 * @version 1.0.0
 * @see OperationAction
 * @since 1.0.0
 */
public class ActionInterceptorHandler extends AbsMonitorOperationInterceptorHandlerSupport implements
        JoinPointInterceptorHandler<OperationActionHandler, AnnotationAttributes> {

    /**
     * @see OperationAction#name()
     * @see OperationAction#value()
     */
    private static final String ATTRIBUTE_NAME = "name";

    /**
     * @see OperationAction#target()
     */
    private static final String ATTRIBUTE_TARGET = "target";

    private static final String ATTRIBUTE_ATTRIBUTES = "attributes";

    /**
     * @see OperationAction#code()
     */
    private static final String ATTRIBUTE_CODE = "code";

    /**
     * @see OperationAction#type()
     */
    private static final String ATTRIBUTE_TYPE = "type";

    /**
     * @see OperationAction#level()
     */
    private static final String ATTRIBUTE_LEVEL = "level";

    /**
     * @see OperationAction#point()
     */
    private static final String ATTRIBUTE_POINT = "point";

    /**
     * @param annotation annotation
     * @return the action join point
     * @see OperationAction#point()
     */
    @Override
    public JoinPoint point(final AnnotationAttributes annotation) {

        return annotation.getEnum(ATTRIBUTE_POINT);
    }

    @Override
    public void handle(final @NonNull OperationActionHandler executor, final @NonNull InvocationContext context,
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

        action.setAttributes(
                Arrays.stream(annotation.getAnnotationArray(ATTRIBUTE_ATTRIBUTES, OperationAction.Attribute.class))
                        .collect(Collectors.toMap(OperationAction.Attribute::name,
                                it -> generateAttribute(it.value(), metadata, evaluationContext)))
        );

        action.setException(throwable);
        action.setTrace(MDC.get("trace"));
        action.setTimestamp(System.currentTimeMillis());

        executor.handle(action);
    }

}
