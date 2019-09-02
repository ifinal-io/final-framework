package org.finalframework.spring.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 调用支持库
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-27 20:57:40
 * @since 1.0
 */
public interface OperationHandlerSupport {

    /**
     * 创建表达式计算域
     *
     * @param context 操作上下文
     * @param result  方法的返回结果，可能为{@code null}
     * @param e       方法抛出的异常，可能为{@code null}
     */
    @NonNull
    EvaluationContext createEvaluationContext(@NonNull OperationContext context, @Nullable Object result, @Nullable Throwable e);

    /**
     * 返回指定的字符串是否为一个表达式
     *
     * @param expression 表达式字符串
     */
    boolean isExpression(@Nullable String expression);

    @NonNull
    String generateExpression(@NonNull String expression);

}
