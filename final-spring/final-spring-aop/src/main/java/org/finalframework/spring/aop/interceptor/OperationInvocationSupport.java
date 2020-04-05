package org.finalframework.spring.aop.interceptor;


import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.core.Assert;
import org.finalframework.spring.aop.*;
import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 09:43:13
 * @since 1.0
 */
public class OperationInvocationSupport {
    private final Map<OperationCacheKey<Operation>, OperationMetadata<Operation>> metadataCache = new ConcurrentHashMap<>(1024);
    private final OperationSource source;
    private final OperationInvocationHandler handler;

    public OperationInvocationSupport(OperationSource source, OperationInvocationHandler handler) {
        this.source = source;
        this.handler = handler;
    }

    private List<OperationContext<Operation>> getOperationContexts(Object target, Method method, Object[] args) {
        final Class<?> targetClass = getTargetClass(target);
        final Collection<? extends Operation> operations = source.getOperations(method, targetClass);
        if (Assert.nonEmpty(operations)) {
            return operations.stream()
                    .map(operation -> getOperationContext(operation, method, args, target, targetClass))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private OperationContext<Operation> getOperationContext(Operation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        OperationMetadata<Operation> metadata = getOperationMetadata(operation, method, targetClass);
        return new BaseOperationContext<>(metadata, target, args);
    }

    private OperationMetadata<Operation> getOperationMetadata(Operation operation, Method method, Class<?> targetClass) {
        final OperationCacheKey<Operation> cacheKey = new OperationCacheKey<>(operation, method, targetClass);
        OperationMetadata<Operation> metadata = this.metadataCache.get(cacheKey);
        if (metadata == null) {
            metadata = new OperationMetadata<>(operation, method, targetClass);
            this.metadataCache.put(cacheKey, metadata);
        }
        return metadata;
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

    protected Object execute(MethodInvocation invocation) throws Throwable {
        final List<OperationContext<Operation>> contexts = getOperationContexts(invocation.getThis(), invocation.getMethod(), invocation.getArguments());
        if (Assert.isEmpty(contexts)) {
            return invocation.proceed();
        }
        return execute(invocation, contexts);
    }

    private Object execute(MethodInvocation invoker, List<OperationContext<Operation>> contexts) throws Throwable {
        Object operationValue = handler.handleBefore(contexts);
        if (operationValue != null) {
            return operationValue;
        }


        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invoker.proceed();
        } catch (Throwable e) {
            throwable = e;
        }

        if (throwable == null) {
            handler.handleAfterReturning(contexts, returnValue);
        } else {
            handler.handleAfterThrowing(contexts, throwable);
        }

        handler.handleAfter(contexts, returnValue, throwable);

        if (throwable != null) {
            if (throwable instanceof OperationException) {
                throw ((OperationException) throwable).getOriginal();
            }
            throw throwable;
        }

        return returnValue;

    }

}
