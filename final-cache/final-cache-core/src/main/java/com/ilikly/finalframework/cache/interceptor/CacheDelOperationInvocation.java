package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheOperationInvocation;
import com.ilikly.finalframework.cache.CacheOperationInvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
public class CacheDelOperationInvocation implements CacheOperationInvocation<Void> {

    @Override
    public Void invoke(Cache cache, CacheOperationInvocationContext context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = context.createEvaluationContext(result);
        if (context.isConditionPassing(evaluationContext)) {
            final Object key = context.generateKey(evaluationContext);
            if (key == null) {
                throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
            }
            final Object field = context.generateField(evaluationContext);
            if (field == null) {
                logger.info("==> cache del: key={}");
                Object flag = cache.del(key);
                logger.info("<== result: {}", flag);
            } else {
                logger.info("==> cache hdel: key={},field={}", key, field);
                Object flag = cache.hdel(key);
                logger.info("<== result: {}", flag);
            }
        }
        return null;

    }

}
