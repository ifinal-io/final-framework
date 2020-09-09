

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
