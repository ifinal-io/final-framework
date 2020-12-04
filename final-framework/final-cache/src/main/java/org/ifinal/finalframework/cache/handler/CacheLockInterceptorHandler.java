package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.Cache;
import org.ifinal.finalframework.cache.CacheLockException;
import org.ifinal.finalframework.cache.annotation.CacheLock;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0.0
 * @see CacheLock
 * @since 1.0.0
 */
@Component
public class CacheLockInterceptorHandler extends AbsCacheOperationInterceptorHandlerSupport implements CacheInterceptorHandler {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String LOCK = "lock";

    @Override
    public Object before(@NonNull Cache cache, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());
        final EvaluationContext evaluationContext = createEvaluationContext(context, null, null);
        MethodMetadata metadata = context.metadata();
        final Object key = generateKey(annotation.getStringArray("key"), annotation.getString("delimiter"), metadata, evaluationContext);
        if (key == null) {
            throw new IllegalArgumentException("the cache action generate null key, action=" + annotation);
        }
        final Object value = Asserts.isEmpty(annotation.getString(VALUE)) ? key : generateValue(annotation.getString(VALUE), metadata, evaluationContext);

        Long ttl;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        Object expired = generateExpire(annotation.getString("expire"), metadata, evaluationContext);

        if (expired != null) {
            if (expired instanceof Date) {
                ttl = ((Date) expired).getTime() - System.currentTimeMillis();
            } else {
                throw new IllegalArgumentException("unSupport expire type: " + expired.getClass());
            }
        } else {
            ttl = annotation.getNumber("ttl");
            timeUnit = annotation.getEnum("timeUnit");
        }

        final long sleep = annotation.getNumber("sleep");
        context.addAttribute(KEY, key);
        context.addAttribute(VALUE, value);

        int retry = 0;
        int maxRetry = annotation.getNumber("retry").intValue();
        do {
            logger.info("==> try to lock: key={},value={},ttl={},timeUnit={},retry={}", key, value, ttl, timeUnit, retry);
            final boolean lock = cache.lock(key, value, ttl, timeUnit);
            logger.info("<== lock result: {}", lock);
            if (lock) {
                context.addAttribute(LOCK, true);
                return null;
            }

            if (sleep > 0L) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    logger.error("retry sleep error,key={},value={}", key, value, e);
                    Thread.currentThread().interrupt();
                }
            }

            retry++;
        } while (retry <= maxRetry);

        context.addAttribute(LOCK, false);
        throw new CacheLockException(String.format("failure to lock key=%s,value=%s", key, value));
    }

    private boolean tryLock(Cache cache, String key, String value, Long ttl, TimeUnit timeUnit) {
        return false;
    }


    @Override
    public void after(@NonNull Cache cache, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation,
                      @Nullable Object result, @Nullable Throwable throwable) {
        final Logger logger = LoggerFactory.getLogger(context.target().getClass());

        final Object key = context.getAttribute(KEY);
        final Object value = context.getAttribute(VALUE);
        final Boolean lock = context.getAttribute(LOCK);

        if (Boolean.TRUE.equals(lock)) {
            logger.info("==> try to unlock: key={},value={}", key, value);
            final boolean unlock = cache.unlock(key, value);
            logger.info("<== result: {}", unlock);
        }
    }

}
