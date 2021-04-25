package org.ifinal.finalframework.context.expression;

import org.springframework.expression.EvaluationContext;

import org.ifinal.finalframework.util.Reflections;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * MethodExpressionEvaluatorTest.
 *
 * @author likly
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
