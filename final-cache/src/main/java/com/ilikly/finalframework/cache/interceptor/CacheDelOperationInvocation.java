package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
public class CacheDelOperationInvocation implements CacheOperationInvocation<CacheDelOperation> {
    @Override
    public Object invoke(CacheOperationInvocationContext<CacheDelOperation> context, CacheOperationInvoker invoker) throws Throwable {
        Logger logger = LoggerFactory.getLogger(context.target().getClass());
        Cache cache = CacheRegistry.getInstance().getCache(context.operation());

        if (cache == null) {
            logger.warn("cannot find cache for cache operation : {} ", context.operation());
            return invoker.invoke();
        }

        Object key = context.generateKey(null);

        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        Object field = context.generateField(null);

        Object result = invoker.invoke();

        if (context.isConditionPassing(result)) {
            Object flag = field == null ? cache.del(key) : cache.hdel(key, field);
            logger.info("delete form cache: key={},field={},result={},flag={}", key, field, result, flag);
        } else {
            logger.info("cache del condition is not passing,key={},field={},result={},condition={}", key, field, result, context.operation().condition());

        }

        return result;

    }
}
