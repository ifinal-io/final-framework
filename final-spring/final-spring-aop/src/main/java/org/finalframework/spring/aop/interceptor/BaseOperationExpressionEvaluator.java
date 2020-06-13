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


import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.finalframework.spring.expression.MethodExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 00:25:39
 * @since 1.0
 */
public class BaseOperationExpressionEvaluator extends MethodExpressionEvaluator implements OperationExpressionEvaluator {

    private final Map<ExpressionKey, Expression> valueCache = new ConcurrentHashMap<>(64);

    @Override
    public Object value(String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.valueCache, methodKey, expression).getValue(evaluationContext);
    }

    @Override
    public <T> T value(String expression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext, Class<T> clazz) {
        return getExpression(this.valueCache, methodKey, expression).getValue(evaluationContext, clazz);
    }

    public void clear() {
        this.valueCache.clear();
    }
}
