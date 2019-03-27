package org.finalframework.spring.aop.interceptor;


import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationSource;
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
 * @date 2019-03-26 12:14:15
 * @since 1.0
 */
public abstract class AbsOperationSource<O extends Operation> implements OperationSource<O> {
    private final Collection<O> NULL_CACHE_OPERATION = Collections.emptyList();
    private final Map<Object, Collection<O>> operationCache = new ConcurrentHashMap<>(1024);

    @Override
    public Collection<O> getOperations(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }

        Object cacheKey = getCacheKey(method, targetClass);
        Collection<O> cached = this.operationCache.get(cacheKey);

        if (cached != null) {
            return (cached != NULL_CACHE_OPERATION ? cached : null);
        }

        Collection<O> cacheOperations = computeCacheOperations(method, targetClass);
        if (cacheOperations != null) {
            this.operationCache.put(cacheKey, cacheOperations);
        } else {
            this.operationCache.put(cacheKey, NULL_CACHE_OPERATION);
        }
        return cacheOperations;
    }

    @Nullable
    private Collection<O> computeCacheOperations(Method method, @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        // First try is the method in the target class.
        Collection<O> opDef = findOperations(specificMethod);

        if (opDef != null) {
            return opDef;
        }

        // Second try is the caching action on the target class.
        opDef = findOperations(specificMethod.getDeclaringClass());
        if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
            return opDef;
        }

        if (specificMethod != method) {
            // Fallback is to look at the original method.
            opDef = findOperations(method);
            if (opDef == null) {
                return opDef;
            }
            // Last fallback is the class of the original method.
            opDef = findOperations(method.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                return opDef;
            }
        }

        return null;
    }

    @Nullable
    protected abstract Collection<O> findOperations(Method method);

    @Nullable
    protected abstract Collection<O> findOperations(Class<?> clazz);

    protected boolean allowPublicMethodsOnly() {
        return false;
    }
}
