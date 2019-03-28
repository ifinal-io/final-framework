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
