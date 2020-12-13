package org.ifinal.finalframework.aop.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ifinal.finalframework.aop.ExpressionEvaluator;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.OperationHandlerSupport;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsOperationInterceptorHandlerSupport implements OperationHandlerSupport {

    /**
     * 表达式的开头标记
     */
    private static final String EXPRESSION_PREFIX = "${";

    /**
     * 表达式的结尾标记
     */
    private static final String EXPRESSION_SUFFIX = "}";

    private static final Pattern EXPRESSION_PATTEN = Pattern.compile("\\$\\{[^\\{\\}]*\\}");

    private final ExpressionEvaluator evaluator;

    public AbsOperationInterceptorHandlerSupport(final ExpressionEvaluator evaluator) {

        this.evaluator = evaluator;
    }

    @Override
    @NonNull
    public EvaluationContext createEvaluationContext(final @NonNull InvocationContext context, final Object result,
        final Throwable e) {

        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
            context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result, e);

    }

    @Override
    public List<String> findExpressions(final String expression) {

        // 指定要匹配的字符串
        Matcher matcher = EXPRESSION_PATTEN.matcher(expression);
        List<String> matchStrs = new ArrayList<>();
        //此处find（）每次被调用后，会偏移到下一个匹配
        while (matcher.find()) {
            //获取当前匹配的值
            matchStrs.add(matcher.group());
        }
        return matchStrs;
    }

    /**
     * 以{@link AbsOperationInterceptorHandlerSupport#EXPRESSION_PREFIX}开头，并且以{@link
     * AbsOperationInterceptorHandlerSupport#EXPRESSION_SUFFIX}结尾的字符串，为一个表达式。
     *
     * @param expression 表达式字符串
     */
    @Override
    public boolean isExpression(final @Nullable String expression) {

        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression
            .endsWith(EXPRESSION_SUFFIX);
    }

    @Override
    @NonNull
    public String generateExpression(final @NonNull String expression) {

        Asserts.isEmpty(expression, "expression is empty");
        return expression.trim()
            .substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }

}
