package org.finalframework.monitor.action.invocation;


import org.finalframework.core.Assert;
import org.finalframework.monitor.action.ActionInvocationSupport;
import org.finalframework.monitor.action.ActionOperation;
import org.finalframework.monitor.action.ActionOperationExpressionEvaluator;
import org.finalframework.monitor.action.OperatorContext;
import org.finalframework.monitor.action.interceptor.DefaultActionOperationExpressionEvaluator;
import org.finalframework.spring.aop.OperationMetadata;
import org.finalframework.spring.aop.interceptor.AbsInvocationSupport;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 11:39:29
 * @since 1.0
 */
public class AbsActionInvocationSupport extends AbsInvocationSupport implements ActionInvocationSupport {
    private final ActionOperationExpressionEvaluator evaluator;

    public AbsActionInvocationSupport() {
        this(new DefaultActionOperationExpressionEvaluator());
    }

    public AbsActionInvocationSupport(ActionOperationExpressionEvaluator evaluator) {
        super(evaluator);
        this.evaluator = evaluator;
    }

    @Override
    public Object generateOperator(String operator, OperationMetadata<? extends ActionOperation> metadata, EvaluationContext evaluationContext) {
        if (Assert.nonBlank(operator)) {
            if (isExpression(operator)) {
                return evaluator.operator(generateExpression(operator), metadata.getMethodKey(), evaluationContext);
            }
            return operator;
        }

        return OperatorContext.getInstance().get();
    }

    @Override
    public Object generateTarget(String target, OperationMetadata<? extends ActionOperation> metadata, EvaluationContext evaluationContext) {
        if (Assert.nonBlank(target) && isExpression(target)) {
            return evaluator.target(generateExpression(target), metadata.getMethodKey(), evaluationContext);
        }
        return target;
    }

    @Override
    public Object generateAttribute(String attribute, OperationMetadata<? extends ActionOperation> metadata, EvaluationContext evaluationContext) {
        if (Assert.nonBlank(attribute) && isExpression(attribute)) {
            return evaluator.attribute(generateExpression(attribute), metadata.getMethodKey(), evaluationContext);
        }
        return attribute;
    }
}
