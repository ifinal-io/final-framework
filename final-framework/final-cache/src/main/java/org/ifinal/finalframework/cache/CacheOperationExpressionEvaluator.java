package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.aop.OperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheOperationExpressionEvaluator extends OperationExpressionEvaluator {

    Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object field(String fieldExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    boolean condition(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object expired(String expiredExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    void clear();

}
