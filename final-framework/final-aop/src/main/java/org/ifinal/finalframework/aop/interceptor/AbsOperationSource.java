package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.Operation;
import org.ifinal.finalframework.aop.OperationSource;
import org.springframework.aop.support.AopUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbsOperationSource implements OperationSource {
    private static final Collection<Operation> NULL_CACHE_OPERATION = Collections.emptyList();
    private final Map<Object, Collection<Operation>> operationCache = new ConcurrentHashMap<>(1024);

    @Override
    public Collection<Operation> getOperations(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return Collections.emptyList();
        }

        Object cacheKey = getCacheKey(method, targetClass);
        Collection<Operation> cached = this.operationCache.get(cacheKey);

        if (cached != null) {
            return (cached != NULL_CACHE_OPERATION ? cached : null);
        }

        Collection<Operation> operations = computeOperations(method, targetClass);
        if (!CollectionUtils.isEmpty(operations)) {
            final ArrayList<? extends Operation> list = new ArrayList<>(operations);
            list.sort(Comparator.comparingInt(Operation::order));
            this.operationCache.put(cacheKey, Collections.unmodifiableList(list));
        } else {
            this.operationCache.put(cacheKey, NULL_CACHE_OPERATION);
        }
        return operations;
    }

    private Collection<Operation> computeOperations(Method method, @Nullable Class<?> targetClass) {

        // Don't allow no-public methods as required.
        if (allowPublicMethodsOnly() && !Modifier.isPublic(method.getModifiers())) {
            return Collections.emptyList();
        }

        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        // 注解可能是声明在接口上的，因此不需要找到实现类的方法
        Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        // First try is the method in the target class.
        Collection<Operation> opDef = findOperations(specificMethod);

        if (opDef != null && !opDef.isEmpty()) {
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
            if (opDef != null && !opDef.isEmpty()) {
                return opDef;
            }
            // Last fallback is the class of the original method.
            opDef = findOperations(method.getDeclaringClass());
            if (opDef != null && ClassUtils.isUserLevelMethod(method)) {
                return opDef;
            }
        }

        return Collections.emptyList();
    }

    @Nullable
    protected abstract Collection<Operation> findOperations(Method method);

    @Nullable
    protected abstract Collection<Operation> findOperations(Class<?> clazz);

    protected boolean allowPublicMethodsOnly() {
        return false;
    }
}
