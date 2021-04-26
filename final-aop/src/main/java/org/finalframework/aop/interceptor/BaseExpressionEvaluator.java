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

package org.finalframework.aop.interceptor;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.finalframework.aop.ExpressionEvaluator;
import org.finalframework.context.expression.MethodExpressionEvaluator;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseExpressionEvaluator extends MethodExpressionEvaluator implements ExpressionEvaluator {

    private final Map<ExpressionKey, Expression> valueCache = new ConcurrentHashMap<>(64);

    @Override
    public Object value(final String expression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext) {

        return getExpression(this.valueCache, methodKey, expression).getValue(evaluationContext);
    }

    @Override
    public <T> T value(final String expression, final AnnotatedElementKey methodKey,
        final EvaluationContext evaluationContext, final Class<T> clazz) {

        return getExpression(this.valueCache, methodKey, expression).getValue(evaluationContext, clazz);
    }

    public void clear() {
        this.valueCache.clear();
    }

}
