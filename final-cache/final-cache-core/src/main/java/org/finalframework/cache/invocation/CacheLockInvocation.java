package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheLockException;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.CacheLock;
import org.finalframework.cache.annotation.enums.CacheInvocationTime;
import org.finalframework.cache.operation.CacheLockOperation;
import org.finalframework.core.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 21:15:34
 * @see CacheLock
 * @since 1.0
 */
public class CacheLockInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheLockOperation, CacheLockInvocationContext, Void, Void> {

    @Override
    public boolean supports(CacheOperationContext<CacheLockOperation, CacheLockInvocationContext> context, CacheInvocationTime invocationTime) {
        return true;
    }

    @Override
    public Void beforeInvocation(Cache cache, CacheOperationContext<CacheLockOperation, CacheLockInvocationContext> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result);
        final CacheLockOperation operation = context.operation();
        final Object key = generateKey(operation.key(), operation.delimiter(), context.metadata(), evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache operation generate null key, operation=" + operation);
        }
        final Object value = Assert.isEmpty(operation.value()) ? key : generateValue(operation.value(), context.metadata(), evaluationContext);

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(operation.expire(), context.metadata(), evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = operation.ttl();
            timeUnit = operation.timeUnit();
        }

        final Long sleep = operation.sleep();

        int count = 0;
        do {
            logger.info("==> try to lock: key={},value={},ttl={},timeunit={},count={}", key, value, timeUnit, timeUnit, count);
            final boolean lock = cache.lock(key, value, ttl, timeUnit);
            logger.info("<== lock result: {}", lock);
            if (lock) {
                context.invocation(new CacheLockInvocationContextImpl(true, key, value));
                return null;
            }

            if (sleep != null && sleep > 0L) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    logger.error("retry sleep error,key={},value={}", e);
                }
            }

            count++;
        } while (count <= operation.retry());
        context.invocation(new CacheLockInvocationContextImpl(false, key, value));
        throw new CacheLockException(String.format("failure to lock key=%s,value=%s", key, value));
    }

    @Override
    public Void afterInvocation(Cache cache, CacheOperationContext<CacheLockOperation, CacheLockInvocationContext> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final CacheLockInvocationContext invocation = context.invocation();
        if (invocation != null && invocation.lock()) {
            logger.info("==> try to unlock: key={},value={}", invocation.key(), invocation.value());
            final boolean unlock = cache.unlock(invocation.key(), invocation.value());
            logger.info("<== result: {}", unlock);
        }
        return null;
    }

    private static class CacheLockInvocationContextImpl implements CacheLockInvocationContext {
        private final boolean lock;
        private final Object key;
        private final Object value;

        public CacheLockInvocationContextImpl(boolean lock, Object key, Object value) {
            this.lock = lock;
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean lock() {
            return lock;
        }

        @Override
        public Object key() {
            return key;
        }

        @Override
        public Object value() {
            return value;
        }
    }

}
