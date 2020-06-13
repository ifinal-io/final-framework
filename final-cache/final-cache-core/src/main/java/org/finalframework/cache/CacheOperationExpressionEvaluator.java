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

package org.finalframework.cache;

import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:54:47
 * @since 1.0
 */
public interface CacheOperationExpressionEvaluator extends OperationExpressionEvaluator {

    Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object field(String fieldExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    boolean condition(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object expired(String expiredExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    void clear();

}
