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

package org.finalframework.monitor.handler;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;

import org.finalframework.aop.interceptor.AbsOperationInterceptorHandlerSupport;
import org.finalframework.context.expression.MethodMetadata;
import org.finalframework.context.user.UserContextHolder;
import org.finalframework.monitor.MonitorExpressionEvaluator;
import org.finalframework.monitor.MonitorOperationHandlerSupport;
import org.finalframework.monitor.annotation.MonitorLevel;
import org.finalframework.monitor.interceptor.DefaultMonitorExpressionEvaluator;
import org.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsMonitorOperationInterceptorHandlerSupport extends AbsOperationInterceptorHandlerSupport implements
    MonitorOperationHandlerSupport {

    private final MonitorExpressionEvaluator evaluator;

    public AbsMonitorOperationInterceptorHandlerSupport() {
        this(new DefaultMonitorExpressionEvaluator());
    }

    public AbsMonitorOperationInterceptorHandlerSupport(final MonitorExpressionEvaluator evaluator) {

        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public String generateName(final String[] name, final String delimiter, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        return evaluator.name(String.join(delimiter, name), metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateOperator(final String operator, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.nonBlank(operator)) {
            return evaluator.operator(operator, metadata.getMethodKey(), evaluationContext);
        }

        return UserContextHolder.getUser();
    }

    @Override
    public Object generateTarget(final String target, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.isBlank(target)) {
            return null;
        }
        return evaluator.target(target, metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public Object generateAttribute(final String attribute, final MethodMetadata metadata,
        final EvaluationContext evaluationContext) {

        if (Asserts.isBlank(attribute)) {
            return null;
        }

        return evaluator.attribute(attribute, metadata.getMethodKey(), evaluationContext);

    }

    @Override
    public MonitorLevel level(final AnnotationAttributes annotation) {
        return annotation.getEnum("level");
    }

}
