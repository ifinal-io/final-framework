package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import com.ilikly.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
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
    @SuppressWarnings("all")
    public Object invoke(CacheOperationInvocationContext<CacheSetOperation> context, CacheOperationInvoker invoker) throws Throwable {
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
        final Type genericReturnType = context.method().getGenericReturnType();
        logger.info("==> try to get from cache: key={},field={},view={}", key, field, context.view());
        Object result = field == null ? cache.get(key, genericReturnType, context.view()) : cache.hget(key, field, genericReturnType, context.view());
        logger.info("<== result: {}", Json.toJson(result));
        if (result != null) {
            return result;
        }
        logger.info("==> try to get frm query: key={},field={}", key, field);
        result = invoker.invoke();
        logger.info("<== result: {}", Json.toJson(result));
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
                    cache.set(key, result, ttl, timeUnit, context.view());
                } else {
                    cache.hset(key, field, result, ttl, timeUnit, context.view());
                }
                logger.info("==> put to cache: key={},field={},result={},ttl={},timeUnit={}", key, field, Json.toJson(result), ttl, timeUnit);
            } else {
                logger.info("cache set condition is not passing,key={},field={},result={},condition={}", key, field, Json.toJson(result), context.operation().condition());
            }
        }

        return result;

    }
}
