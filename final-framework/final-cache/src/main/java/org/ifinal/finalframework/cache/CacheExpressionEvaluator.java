package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.aop.ExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheExpressionEvaluator extends ExpressionEvaluator {

    Object key(final String keyExpression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext);

    Object field(final String fieldExpression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext);

    boolean condition(final String conditionExpression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext);

    Object expired(final String expiredExpression, final AnnotatedElementKey methodKey, final EvaluationContext evaluationContext);

    void clear();

}
