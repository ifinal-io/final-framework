package org.finalframework.monitor;

import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 11:25:47
 * @since 1.0
 */
public interface MonitorOperationExpressionEvaluator extends OperationExpressionEvaluator {

    String name(String nameExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);
    Object operator(String operatorExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object target(String targetExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);

    Object attribute(String attributeExpression, AnnotatedElementKey methodKey, EvaluationContext evaluationContext);


}
