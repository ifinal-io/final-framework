package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.cache.CacheOperationSource;
import org.springframework.aop.support.AopUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:16:35
 * @since 1.0
 */
public abstract class AbsCacheOperationSource implements CacheOperationSource {

    private static final Collection<CacheOperation> NULL_CACHE_OPERATION = Collections.emptyList();
    private final Map<Object, Collection<CacheOperation>> cacheOperationCache = new ConcurrentHashMap<>(1024);

    @Override
    public Collection<CacheOperation> getCacheOperations(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) return null;

        Object cacheKey = getCacheKey(method, targetClass);
        Collection<CacheOperation> cached = this.cacheOperationCache.get(cacheKey);

        if (cached != null) {
            return (cached != NULL_CACHE_OPERATION ? cached : null);
        }

        Collection<CacheOperation> cacheOperations = computeCacheOperations(method, targetClass);
        if (cacheOperations != null) {
            this.cacheOperationCache.put(cacheKey, cacheOperations);
        } else {
            this.cacheOperationCache.put(cacheKey, NULL_CACHE_OPERATION);
        }
        return cacheOperations;
    }

    @Nullable
    private Collection<CacheOperation> computeCacheOperations(Method method, @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }


        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        // First try is the method in the target class.
        Collection<CacheOperation> opDef = findCacheOperations(specificMethod);

        if (opDef != null) {
            return opDef;
        }

        // Second try is the caching operation on the target class.
        opDef = findCacheOperations(specificMethod.getDeclaringClass());
        if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
            return opDef;
        }

        if (specificMethod != method) {
            // Fallback is to look at the original method.
            opDef = findCacheOperations(method);
            if (opDef == null) {
                return opDef;
            }
            // Last fallback is the class of the original method.
            opDef = findCacheOperations(method.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                return opDef;
            }
        }

        return null;
    }

    @Nullable
    protected abstract Collection<CacheOperation> findCacheOperations(Method method);

    @Nullable
    protected abstract Collection<CacheOperation> findCacheOperations(Class<?> clazz);

    protected boolean allowPublicMethodsOnly() {
        return false;
    }

}
