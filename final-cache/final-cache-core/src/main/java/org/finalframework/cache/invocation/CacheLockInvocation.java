package org.finalframework.cache.invocation;

import org.finalframework.cache.Cache;
import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheLockException;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.annotation.CacheLock;
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
public class CacheLockInvocation extends AbsCacheInvocationSupport implements CacheInvocation<CacheLockOperation, CacheLockProperty> {

    @Override
    public Void before(Cache cache, CacheOperationContext<CacheLockOperation, CacheLockProperty> context, Object result) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, result, null);
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
                context.property(new CacheLockPropertyImpl(true, key, value));
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
        context.property(new CacheLockPropertyImpl(false, key, value));
        throw new CacheLockException(String.format("failure to lock key=%s,value=%s", key, value));
    }

    @Override
    public void after(Cache cache, CacheOperationContext<CacheLockOperation, CacheLockProperty> context, Object result, Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final CacheLockProperty property = context.property();
        if (property != null && property.lock()) {
            logger.info("==> try to unlock: key={},value={}", property.key(), property.value());
            final boolean unlock = cache.unlock(property.key(), property.value());
            logger.info("<== result: {}", unlock);
        }
    }

    private static class CacheLockPropertyImpl implements CacheLockProperty {
        private final boolean lock;
        private final Object key;
        private final Object value;

        public CacheLockPropertyImpl(boolean lock, Object key, Object value) {
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
