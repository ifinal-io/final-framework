package com.ilikly.finalframework.cache;

import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 */
public interface CacheOperationInvocation<T> {
    T invoke(Cache cache, CacheOperationInvocationContext context, Object result, EvaluationContext evaluationContext);
}
