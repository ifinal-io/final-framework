package org.ifinal.finalframework.aop;

import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 调用支持库
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
    EvaluationContext createEvaluationContext(@NonNull InvocationContext context, @Nullable Object result,
        @Nullable Throwable e);

}
