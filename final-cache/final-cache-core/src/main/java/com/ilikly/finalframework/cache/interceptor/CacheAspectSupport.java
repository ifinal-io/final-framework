package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.*;
import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;
import com.ilikly.finalframework.cache.handler.CacheDelOperationContextsHandler;
import com.ilikly.finalframework.cache.handler.CacheLockOperationContextsHandler;
import com.ilikly.finalframework.cache.handler.CachePutOperationContextsHandler;
import com.ilikly.finalframework.cache.handler.CacheableOperationContextsHandler;
import com.ilikly.finalframework.core.Assert;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.util.*;
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
    private final Map<CacheOperationCacheKey, CacheOperationMetadata> metadataCache = new ConcurrentHashMap<>(1024);
    private final CacheConfiguration cacheConfiguration = new CacheConfiguration();
    private final List<CacheOperationContextsHandler<?>> beforeCacheOperationContextsHandlers = new ArrayList<>();
    private final List<CacheOperationContextsHandler<?>> afterCacheOperationContextsHandlers = new ArrayList<>();
    private boolean initialized = true;
    @Setter
    @Getter
    private CacheOperationSource cacheOperationSource;

    {
        final CacheLockOperationContextsHandler cacheLockOperationContextsHandler = new CacheLockOperationContextsHandler();
        final CacheableOperationContextsHandler cacheableOperationContextsHandler = new CacheableOperationContextsHandler();
        final CacheDelOperationContextsHandler cacheDelOperationContextsHandler = new CacheDelOperationContextsHandler();
        beforeCacheOperationContextsHandlers.add(cacheLockOperationContextsHandler);
        beforeCacheOperationContextsHandlers.add(cacheableOperationContextsHandler);
        beforeCacheOperationContextsHandlers.add(cacheDelOperationContextsHandler);

        final CachePutOperationContextsHandler cachePutOperationContextsHandler = new CachePutOperationContextsHandler();
        afterCacheOperationContextsHandlers.add(cacheableOperationContextsHandler);
        afterCacheOperationContextsHandlers.add(cachePutOperationContextsHandler);
        afterCacheOperationContextsHandlers.add(cacheDelOperationContextsHandler);
        afterCacheOperationContextsHandlers.add(cacheLockOperationContextsHandler);
    }

    protected CacheContext getCacheOperationContext(CacheOperation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        CacheOperationMetadata metadata = getCacheOperationMetadata(operation, method, targetClass);
        return new CacheContext(evaluator, metadata, target, args);
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
                    return execute(invoker, method, new CacheOperationContextsImpl(cacheConfiguration, operations, method, args, target, targetClass));
                }
            }
        }

        return invoker.invoke();
    }

    @SuppressWarnings("unchecked")
    private Object execute(CacheOperationInvoker invoker, Method method, CacheOperationContextsImpl contexts) {

        Object cacheValue = null;
        for (CacheOperationContextsHandler<?> handler : beforeCacheOperationContextsHandlers) {
            Object value = handler.handle(contexts, DefaultCacheOperationExpressionEvaluator.NO_RESULT, CacheInvocationTime.BEFORE);
            if (cacheValue == null && value != null) {
                cacheValue = value;
            }
        }

        if (cacheValue != null) {
            return cacheValue;
        }

        final Object returnValue = invoker.invoke();
        for (CacheOperationContextsHandler<?> handler : afterCacheOperationContextsHandlers) {
            handler.handle(contexts, returnValue, CacheInvocationTime.AFTER);
        }
        return returnValue;

    }

    private class CacheOperationContextsImpl implements CacheOperationContexts {
        private final CacheConfiguration configuration;
        private final MultiValueMap<Class<? extends CacheOperation>, CacheOperationContext> contexts;

        public CacheOperationContextsImpl(CacheConfiguration configuration, Collection<? extends CacheOperation> operations, Method method, Object[] args, Object target, Class<?> targetClass) {
            this.configuration = configuration;
            this.contexts = new LinkedMultiValueMap<>(operations.size());
            for (CacheOperation operation : operations) {
                final CacheOperationContext cacheOperationContext = getCacheOperationContext(operation, method, args, target, targetClass);
                this.contexts.add(operation.getClass(), cacheOperationContext);
            }
        }

        @Override
        public CacheConfiguration configuration() {
            return configuration;
        }

        @Override
        public <O extends CacheOperation> Collection<CacheOperationContext> get(Class<O> operation) {
            final List<CacheOperationContext> result = this.contexts.get(operation);
            return result != null ? result : Collections.emptyList();
        }
    }


}
