package org.ifinal.finalframework.context.expression;

import java.lang.reflect.Method;
import org.ifinal.finalframework.util.Reflections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;

/**
 * MethodExpressionEvaluatorTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class MethodExpressionEvaluatorTest {

    void method(Long id, String name) {

    }

    @Test
    void test() {
        Method method = Reflections.findRequiredMethod(MethodExpressionEvaluatorTest.class, "method", Long.class, String.class);
        MethodExpressionEvaluator evaluator = new MethodExpressionEvaluator();

        EvaluationContext context = evaluator
            .createEvaluationContext(method, new Object[]{1L, "name"}, this, MethodExpressionEvaluatorTest.class, method,
                MethodExpressionEvaluator.NO_RESULT, null);

        Object value = new Spel().expression("#id").getValue(context);

        Assertions.assertEquals(1L, value);

    }

}