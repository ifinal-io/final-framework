package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import com.ilikly.finalframework.core.Assert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 18:00:41
 * @since 1.0
 */
@Slf4j
public abstract class CacheAspectSupport {
    private final CacheOperationExpressionEvaluator evaluator = new DefaultCacheOperationExpressionEvaluator();
    private static final CacheLockOperationInvocation cacheLockOperationInvocation = new CacheLockOperationInvocation();
    private static final CacheableOperationInvocation cacheableOperationInvocation = new CacheableOperationInvocation();
    private static final CacheGetOperationInvocation cacheGetOperationInvocation = new CacheGetOperationInvocation();
    private static final CacheDelOperationInvocation cacheDelOperationInvocation = new CacheDelOperationInvocation();
    private final Map<CacheOperationCacheKey, CacheOperationMetadata> metadataCache = new ConcurrentHashMap<>(1024);
    private boolean initialized = true;
    @Setter
    @Getter
    private CacheOperationSource cacheOperationSource;

    protected CacheOperationContext getCacheOperationContext(CacheOperation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        CacheOperationMetadata metadata = getCacheOperationMetadata(operation, method, targetClass);
        return new CacheOperationContext(evaluator, metadata, target, args);
    }

    protected CacheOperationMetadata getCacheOperationMetadata(CacheOperation operation, Method method, Class<?> targetClass) {
        CacheOperationCacheKey cacheKey = new CacheOperationCacheKey(operation, method, targetClass);
        CacheOperationMetadata metadata = this.metadataCache.get(cacheKey);
        if (metadata == null) {
            metadata = new CacheOperationMetadata(operation, method, targetClass);
            this.metadataCache.put(cacheKey, metadata);
        }
        return metadata;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }


    protected Object execute(CacheOperationInvoker invoker, Object target, Method method, Object[] args) {
        if (initialized) {
            CacheOperationSource cacheOperationSource = getCacheOperationSource();
            final Class<?> targetClass = getTargetClass(target);
            if (cacheOperationSource != null) {
                final Collection<CacheOperation> operations = cacheOperationSource.getCacheOperations(method, targetClass);
                if (Assert.nonEmpty(operations)) {
                    return execute(invoker, method, new CacheOperationContexts(operations, method, args, target, targetClass));
                }
            }
        }

        return invoker.invoke();
    }

    private Object execute(CacheOperationInvoker invoker, Method method, CacheOperationContexts contexts) {

        CacheLockContext cacheLockContext = null;
        try {

            if (Assert.nonEmpty(contexts.get(CacheLockOperation.class))) {
                final CacheOperationContext context = contexts.get(CacheLockOperation.class).iterator().next();
                final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
                cacheLockContext = cacheLockOperationInvocation.invoke(cache, context, DefaultCacheOperationExpressionEvaluator.NO_RESULT);
                if (!cacheLockContext.isLock()) return null;
            }


            if (Assert.nonEmpty(contexts.get(CacheableOperation.class))) {
                final CacheOperationContext context = contexts.get(CacheableOperation.class).iterator().next();
                final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
                final Object cacheValue = cacheGetOperationInvocation.invoke(cache, context, DefaultCacheOperationExpressionEvaluator.NO_RESULT);
                if (cacheValue != null) return cacheValue;
            }

            processCacheDel(contexts.get(CacheDelOperation.class), true, DefaultCacheOperationExpressionEvaluator.NO_RESULT);

            final Object returnValue = invoker.invoke();

            processCacheSet(contexts.get(CacheableOperation.class), returnValue);
            processCacheSet(contexts.get(CachePutOperation.class), returnValue);
            processCacheDel(contexts.get(CacheDelOperation.class), false, returnValue);
            return returnValue;
        } finally {
            if (cacheLockContext != null && cacheLockContext.isLock()) {
                logger.info("==> try to unlock: key={},value={}", cacheLockContext.getKey(), cacheLockContext.getValue());
                final boolean unlock = cacheLockContext.unlock();
                logger.info("<== result: {}", unlock);
            }
        }

    }

    private void processCacheSet(Collection<CacheOperationContext> contexts, Object returnValue) {
        for (CacheOperationContext context : contexts) {
            performCacheSet(context, context.operation(), returnValue);
        }
    }

    private void performCacheSet(CacheOperationContext context, CacheOperation operation, Object returnValue) {
        final Cache cache = CacheRegistry.getInstance().getCache(operation);
        cacheableOperationInvocation.invoke(cache, context, returnValue);
    }


    private void processCacheDel(Collection<CacheOperationContext> contexts, boolean beforeInvocation, @Nullable Object result) {
        for (CacheOperationContext context : contexts) {
            final CacheDelOperation operation = (CacheDelOperation) context.operation();
            if (beforeInvocation == operation.isBeforeInvocation()) {
                performCacheDel(context, operation, result);
            }
        }
    }

    private void performCacheDel(CacheOperationContext context, CacheDelOperation operation, Object result) {
        final Cache cache = CacheRegistry.getInstance().getCache(operation);
        cacheDelOperationInvocation.invoke(cache, context, result);
    }

    private EvaluationContext createEvaluationContext(CacheOperationContext context, @Nullable Object result) {
        return evaluator.createEvaluationContext(context.metadata().getMethod(), context.args(),
                context.target(), context.metadata().getTargetClass(), context.metadata().getTargetMethod(), result);
    }

    private class CacheOperationContexts {
        private final MultiValueMap<Class<? extends CacheOperation>, CacheOperationContext> contexts;

        public CacheOperationContexts(Collection<? extends CacheOperation> operations, Method method, Object[] args, Object target, Class<?> targetClass) {
            this.contexts = new LinkedMultiValueMap<>(operations.size());
            for (CacheOperation operation : operations) {
                final CacheOperationContext cacheOperationContext = getCacheOperationContext(operation, method, args, target, targetClass);
                this.contexts.add(operation.getClass(), cacheOperationContext);
            }
        }

        public Collection<CacheOperationContext> get(Class<? extends CacheOperation> operationClass) {
            final List<CacheOperationContext> result = this.contexts.get(operationClass);
            return result != null ? result : Collections.emptyList();
        }
    }


}
