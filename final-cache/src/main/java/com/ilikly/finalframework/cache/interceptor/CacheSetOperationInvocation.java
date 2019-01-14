package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
public class CacheSetOperationInvocation implements CacheOperationInvocation<CacheSetOperation> {
    @Override
    public Object invoke(CacheOperationInvocationContext<CacheSetOperation> context, CacheOperationInvoker invoker) throws Throwable {
        Logger logger = LoggerFactory.getLogger(context.target().getClass());
        Cache cache = CacheRegistry.getInstance().getCache(context.operation());

        if (cache == null) {
            logger.warn("cannot find cache for cache operation : {} ", context.operation());
            return invoker.invoke();
        }

        Object key = context.generateKey(null);

        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null keys, operation=" + context.operation());
        }
        Object field = context.generateField(null);

        Object result = field == null ? cache.get(key) : cache.hget(key, field);

        if (result != null) {
            logger.info("get from cache: keys={},fields={},result={}", key, field, result);
            return result;
        }

        result = invoker.invoke();
        logger.info("get from query: keys={},fields={},result={}", key, field, result);
        if (result != null) {
            if (context.isConditionPassing(result)) {
                Long ttl;
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                Object expired = context.generateExpire(result);

                if (expired != null) {
                    if (expired instanceof Date) {
                        ttl = ((Date) expired).getTime() - System.currentTimeMillis();
                    } else {
                        throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
                    }
                } else {
                    ttl = context.operation().ttl();
                    timeUnit = context.operation().timeUnit();
                }

                if (field == null) {
                    cache.set(key, result, ttl, timeUnit);
                } else {
                    cache.hset(key, field, result, ttl, timeUnit);
                }
                logger.info("set to cache: keys={},fields={},result={},ttl={},timeUnit={}", key, field, result, ttl, timeUnit);
            } else {
                logger.info("cache set condition is not passing,keys={},fields={},result={},condition={}", key, field, result, context.operation().condition());
            }
        }

        return result;

    }
}
