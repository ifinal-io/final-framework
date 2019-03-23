package org.finalframework.cache.interceptor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.finalframework.cache.*;
import org.finalframework.core.Assert;
import org.springframework.aop.framework.AopProxyUtils;
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
    private final Map<CacheOperationCacheKey, CacheOperationMetadata> metadataCache = new ConcurrentHashMap<>(1024);
    private boolean initialized = true;
    @Setter
    @Getter
    private CacheOperationSource cacheOperationSource;
    @Setter
    private CacheConfiguration cacheConfiguration;

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
        for (CacheInvocationHandler<?, ?> handler : cacheConfiguration.getCacheInvocationHandlers()) {
            Object value = handler.handleBefore(contexts, DefaultCacheOperationExpressionEvaluator.NO_RESULT);
            if (cacheValue == null && value != null) {
                cacheValue = value;
            }
        }

        if (cacheValue != null) {
            return cacheValue;
        }

        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invoker.invoke();
        } catch (Throwable e) {
            throwable = e;
        }

        for (CacheInvocationHandler<?, ?> handler : cacheConfiguration.getCacheInvocationHandlers()) {
            handler.handleAfter(contexts, returnValue, throwable);
        }

        if (throwable != null) {
            throw new CacheException(throwable);
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
