package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.*;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class OperationInvocationSupport {
    private final Map<OperationCacheKey<Operation>, OperationMetadata<Operation>> metadataCache = new ConcurrentHashMap<>(1024);
    private final OperationConfiguration configuration;

    public OperationInvocationSupport(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    protected List<OperationContext<Operation>> getOperationContexts(Object target, Method method, Object[] args) {
        final Class<?> targetClass = getTargetClass(target);
        final Collection<? extends Operation> operations = configuration.getOperationSource().getOperations(method, targetClass);

        if (CollectionUtils.isEmpty(operations)) {
            return Collections.emptyList();
        }

        return operations.stream()
                .map(operation -> getOperationContext(operation, method, args, target, targetClass))
                .collect(Collectors.toList());
    }

    private OperationContext<Operation> getOperationContext(Operation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        OperationMetadata<Operation> metadata = getOperationMetadata(operation, method, targetClass);
        return new BaseOperationContext<>(metadata, target, args);
    }

    private OperationMetadata<Operation> getOperationMetadata(Operation operation, Method method, Class<?> targetClass) {
        final OperationCacheKey<Operation> cacheKey = new OperationCacheKey<>(operation, method, targetClass);
        return this.metadataCache.computeIfAbsent(cacheKey, key -> new OperationMetadata<>(operation, method, targetClass));
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

}
