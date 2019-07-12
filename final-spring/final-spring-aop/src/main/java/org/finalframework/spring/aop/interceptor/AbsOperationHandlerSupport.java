package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationExpressionEvaluator;
import org.finalframework.spring.aop.OperationHandlerSupport;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:13:02
 * @since 1.0
 */
public class AbsOperationHandlerSupport implements OperationHandlerSupport {

    /**
     * 表达式的开头标记
     */
    private static final String EXPRESSION_PREFIX = "{";
    /**
     * 表达式的结尾标记
     */
    private static final String EXPRESSION_SUFFIX = "}";

    private final OperationExpressionEvaluator evaluator;

    public AbsOperationHandlerSupport(OperationExpressionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    @Override
    public EvaluationContext createEvaluationContext(OperationContext context, Object result, Throwable e) {
        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
                context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result, e);

    }

    /**
     * 以{@link AbsOperationHandlerSupport#EXPRESSION_PREFIX}开头，并且以{@link AbsOperationHandlerSupport#EXPRESSION_SUFFIX}结尾的字符串，为一个表达式。
     *
     * @param expression 表达式字符串
     */
    @Override
    public boolean isExpression(@Nullable String expression) {
        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression.endsWith(EXPRESSION_SUFFIX);
    }

    @Override
    public String generateExpression(@NonNull String expression) {
        Assert.isEmpty(expression, "expression is empty");
        return expression.trim().substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }
}
