package org.ifinal.finalframework.aop.interceptor;


import org.ifinal.finalframework.aop.AnnotationInvocationContext;
import org.ifinal.finalframework.aop.OperationConfiguration;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.annotation.AnnotationAttributes;
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

    private final Map<AnnotatedElementKey, MethodMetadata> metadataCache = new ConcurrentHashMap<>(1024);
    private final OperationConfiguration configuration;

    public OperationInvocationSupport(OperationConfiguration configuration) {
        this.configuration = configuration;
    }

    protected List<AnnotationInvocationContext> getOperationContexts(Object target, Method method, Object[] args) {
        final Class<?> targetClass = getTargetClass(target);
        final Collection<AnnotationAttributes> operations = configuration.getOperationSource().getOperations(method, targetClass);

        if (CollectionUtils.isEmpty(operations)) {
            return Collections.emptyList();
        }

        return operations.stream()
                .map(operation -> getOperationContext(operation, method, args, target, targetClass))
                .collect(Collectors.toList());
    }

    private AnnotationInvocationContext getOperationContext(AnnotationAttributes operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        MethodMetadata metadata = getOperationMetadata(method, targetClass);
        return new BaseAnnotationInvocationContext(operation, metadata, target, args);
    }

    private MethodMetadata getOperationMetadata(Method method, Class<?> targetClass) {
        final AnnotatedElementKey cacheKey = new AnnotatedElementKey(method, targetClass);
        return this.metadataCache.computeIfAbsent(cacheKey, key -> new MethodMetadata(method, targetClass));
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

}
