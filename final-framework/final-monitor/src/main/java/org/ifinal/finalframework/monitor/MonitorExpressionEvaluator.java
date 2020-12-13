package org.ifinal.finalframework.monitor;

import org.ifinal.finalframework.aop.ExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface MonitorExpressionEvaluator extends ExpressionEvaluator {

    String name(String nameExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object operator(String operatorExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object target(String targetExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object attribute(String attributeExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

}
