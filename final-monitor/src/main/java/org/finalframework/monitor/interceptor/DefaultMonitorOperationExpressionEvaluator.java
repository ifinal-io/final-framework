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

package org.finalframework.monitor.interceptor;


import org.finalframework.monitor.MonitorOperationExpressionEvaluator;
import org.finalframework.spring.aop.interceptor.BaseOperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 11:31:54
 * @since 1.0
 */
public class DefaultMonitorOperationExpressionEvaluator extends BaseOperationExpressionEvaluator implements MonitorOperationExpressionEvaluator {
    private final Map<ExpressionKey, Expression> nameCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> operatorCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> targetCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> attributeCache = new ConcurrentHashMap<>(64);

    @Override
    public String name(String nameExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.nameCache, methodKey, nameExpression).getValue(evaluationContext).toString();
    }

    @Override
    public Object operator(String operatorExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.operatorCache, methodKey, operatorExpression).getValue(evaluationContext);
    }

    @Override
    public Object target(String targetExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
        return getExpression(this.targetCache, methodKey, targetExpression).getValue(evaluationContext);
    }

    @Override
    public Object attribute(String attributeExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext) {
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
