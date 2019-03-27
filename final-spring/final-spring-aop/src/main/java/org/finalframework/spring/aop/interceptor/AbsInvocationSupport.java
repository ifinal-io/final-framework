package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.InvocationSupport;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:13:02
 * @since 1.0
 */
public class AbsInvocationSupport implements InvocationSupport {

    private static final String EXPRESSION_PREFIX = "{";
    private static final String EXPRESSION_SUFFIX = "}";

    private final OperationExpressionEvaluator evaluator;

    public AbsInvocationSupport(OperationExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public EvaluationContext createEvaluationContext(OperationContext context, Object result, Throwable e) {
        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
                context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result, e);

    }

    @Override
    public boolean isExpression(String expression) {
        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression.endsWith(EXPRESSION_SUFFIX);
    }

    @Override
    public String generateExpression(@NonNull String expression) {
        return expression.substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }
}
