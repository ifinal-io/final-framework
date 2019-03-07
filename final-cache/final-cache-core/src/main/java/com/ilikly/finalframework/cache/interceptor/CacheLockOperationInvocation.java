package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.Cache;
import com.ilikly.finalframework.cache.CacheOperationInvocation;
import com.ilikly.finalframework.cache.CacheOperationInvocationContext;
import com.ilikly.finalframework.core.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see com.ilikly.finalframework.cache.annotation.CacheLock
 * @see CacheableOperation
 * @since 1.0
 */
public class CacheLockOperationInvocation implements CacheOperationInvocation<CacheLockContext> {

    @Override
    @SuppressWarnings("all")
    @NonNull
    public CacheLockContext invoke(Cache cache, CacheOperationInvocationContext context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = context.createEvaluationContext(result);
        final Object key = Assert.isEmpty(context.operation().key()) ? UUID.randomUUID().toString() : context.generateKey(evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + context.operation());
        }
        final Object value = Assert.isEmpty(context.operation().value()) ? key : context.generateValue(evaluationContext);


        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = context.generateExpire(evaluationContext);

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

        final Long sleep = context.operation().sleep();

        int count = 1;
        do {
            logger.info("==> try to lock: key={},value={},ttl={},timeunit={},count={}", key, value, timeUnit, timeUnit, count);
            final boolean lock = cache.lock(key, value, ttl, timeUnit);
            logger.info("<== lock result: {}", lock);
            if (lock) return new CacheLockContext(true, key, value, cache);

            if (sleep != null && sleep > 0L) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    logger.error("retry sleep error,key={},value={}", e);
                }
            }

            count++;
        } while (count <= context.operation().retry());


        return new CacheLockContext(false, key, value, cache);

    }
}
