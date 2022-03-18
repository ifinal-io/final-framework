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

package org.ifinalframework.monitor.interceptor;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import org.ifinalframework.aop.interceptor.BaseExpressionEvaluator;
import org.ifinalframework.monitor.MonitorExpressionEvaluator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultMonitorExpressionEvaluator extends BaseExpressionEvaluator implements MonitorExpressionEvaluator {

    private final Map<ExpressionKey, Expression> nameCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> operatorCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> targetCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> attributeCache = new ConcurrentHashMap<>(64);

    @Override
    public String name(final String nameExpression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext) {

        return String.valueOf(getExpression(this.nameCache, methodKey, nameExpression).getValue(evaluationContext));
    }

    @Override
    public Object operator(final String operatorExpression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext) {

        return getExpression(this.operatorCache, methodKey, operatorExpression).getValue(evaluationContext);
    }

    @Override
    public Object target(final String targetExpression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext) {

        return getExpression(this.targetCache, methodKey, targetExpression).getValue(evaluationContext);
    }

    @Override
    public Object attribute(final String attributeExpression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext) {

        return getExpression(this.attributeCache, methodKey, attributeExpression).getValue(evaluationContext);
    }

    @Override
    public void clear() {
        super.clear();
        operatorCache.clear();
        targetCache.clear();
        attributeCache.clear();
    }

}
