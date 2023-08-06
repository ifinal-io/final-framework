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

package org.ifinalframework.context.expression;

import org.springframework.expression.EvaluationContext;

import org.ifinalframework.util.Reflections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * MethodExpressionEvaluatorTest.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
class MethodExpressionEvaluatorTest {

    void method(final Long id, final String name) {

    }

    @Test
    void test() {
        Method method = Reflections
                .findRequiredMethod(MethodExpressionEvaluatorTest.class, "method", Long.class, String.class);
        MethodExpressionEvaluator evaluator = new MethodExpressionEvaluator();

        EvaluationContext context = evaluator
                .createEvaluationContext(method, new Object[]{1L, "name"}, this, MethodExpressionEvaluatorTest.class,
                        method,
                        MethodExpressionEvaluator.NO_RESULT, null);

        Object value = Spel.getValue("#{#id}", context);

        Assertions.assertEquals(1L, value);

    }

}
