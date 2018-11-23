package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @since 1.0
 */
@Slf4j
public class CacheSetOperationInvocation implements CacheOperationInvocation<CacheSetOperation> {
    @Override
    public Object invoke(CacheOperationInvocationContext<CacheSetOperation> context, CacheOperationInvoker invoker) throws Throwable {
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

        Object result = field == null ? cache.get(key) : cache.hget(key, field);

        if (result != null) {
            logger.info("get from cache: key={},field={},result={}", key, field, result);
            return result;
        }

        result = invoker.invoke();
        logger.info(" get from query: key={},field={},result={}", key, field, result);
        if (result != null) {
            if (context.isConditionPassing(result)) {
                Long ttl;
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                Object expired = context.generateExpired(result);

                if (expired != null) {
                    if (expired instanceof Date) {
                        ttl = ((Date) expired).getTime() - System.currentTimeMillis();
                    } else {
                        throw new IllegalArgumentException("unSupport expired type: " + expired.getClass());
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
                logger.info("set to cache: key={},field={},result={},ttl={},timeUnit={}", key, field, result, ttl, timeUnit);
            } else {
                logger.info("cache set condition is not passing,key={},field={},result={},condition={}", key, field, result, context.operation().condition());
            }
        }

        return result;

    }
}
