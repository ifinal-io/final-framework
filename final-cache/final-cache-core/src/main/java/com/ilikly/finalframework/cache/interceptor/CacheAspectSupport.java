package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import com.ilikly.finalframework.core.Assert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
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
    private static final CacheGetOperationInvocation cacheGetOperationInvocation = new CacheGetOperationInvocation();
    private final CacheOperationExpressionEvaluator evaluator = new DefaultCacheOperationExpressionEvaluator();
    private static final CacheDelOperationInvocation cacheDelOperationInvocation = new CacheDelOperationInvocation();
    private static final CacheableOperationInvocation CACHEABLE_OPERATION_INVOCATION = new CacheableOperationInvocation();
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
        final CacheOperationContext context = contexts.get(CacheableOperation.class).iterator().next();
        final Cache cache = CacheRegistry.getInstance().getCache(context.operation());
        final EvaluationContext evaluationContext = createEvaluationContext(context, DefaultCacheOperationExpressionEvaluator.NO_RESULT);
        final Object cacheValue = cacheGetOperationInvocation.invoke(cache, context, null, evaluationContext);
        if (cacheValue != null) return cacheValue;

        processCacheDel(contexts.get(CacheDelOperation.class), true, null, evaluationContext);

        final Object returnValue = invoker.invoke();

        final EvaluationContext returnEvaluationContext = createEvaluationContext(context, returnValue);
        processCacheSet(contexts.get(CacheableOperation.class), returnValue, returnEvaluationContext);
        processCacheSet(contexts.get(CachePutOperation.class), returnValue, returnEvaluationContext);
        processCacheDel(contexts.get(CacheDelOperation.class), false, returnValue, returnEvaluationContext);
        return returnValue;

    }

    private void processCacheSet(Collection<CacheOperationContext> contexts, Object returnValue, @NonNull EvaluationContext evaluationContext) {
        for (CacheOperationContext context : contexts) {
            if (context.isConditionPassing(evaluationContext)) {
                performCacheSet(context, context.operation(), returnValue, evaluationContext);
            }
        }
    }

    private void performCacheSet(CacheOperationContext context, CacheOperation operation, Object returnValue, @NonNull EvaluationContext evaluationContext) {
        final Cache cache = CacheRegistry.getInstance().getCache(operation);
        CACHEABLE_OPERATION_INVOCATION.invoke(cache, context, returnValue, evaluationContext);
    }


    private void processCacheDel(Collection<CacheOperationContext> contexts, boolean beforeInvocation, @Nullable Object result, @NonNull EvaluationContext evaluationContext) {
        for (CacheOperationContext context : contexts) {
            final CacheDelOperation operation = (CacheDelOperation) context.operation();
            if (beforeInvocation == operation.isBeforeInvocation() && context.isConditionPassing(evaluationContext)) {
                performCacheDel(context, operation, result, evaluationContext);
            }
        }
    }

    private void performCacheDel(CacheOperationContext context, CacheDelOperation operation, Object result, @NonNull EvaluationContext evaluationContext) {
        final Cache cache = CacheRegistry.getInstance().getCache(operation);
        cacheDelOperationInvocation.invoke(cache, context, result, evaluationContext);
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
