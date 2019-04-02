package org.finalframework.spring.aop.interceptor;


import org.finalframework.core.Assert;
import org.finalframework.spring.aop.*;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 09:43:13
 * @since 1.0
 */
public class OperationAspectSupport {
    private final Map<OperationCacheKey<Operation>, OperationMetadata<Operation>> metadataCache = new ConcurrentHashMap<>(1024);
    private OperationConfiguration configuration;
    private OperationSource<Operation> source;

    public OperationAspectSupport(OperationConfiguration configuration) {
        this.configuration = configuration;
        this.source = new AnnotationOperationSource<>(configuration);
    }

    protected OperationContext<Operation> getCacheOperationContext(Operation operation, Method method, Object[] args, Object target, Class<?> targetClass) {
        OperationMetadata<Operation> metadata = getOperationMetadata(operation, method, targetClass);
        return new BaseOperationContext<>(metadata, target, args);
    }

    protected OperationMetadata<Operation> getOperationMetadata(Operation operation, Method method, Class<?> targetClass) {
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


    protected Object execute(OperationInvoker invoker, Object target, Method method, Object[] args) throws Throwable {
        final Class<?> targetClass = getTargetClass(target);
        final Collection<Operation> operations = source.getOperations(method, targetClass);
        if (Assert.nonEmpty(operations)) {
            final OperationContexts operationContexts = new OperationContextsImpl(configuration, operations, method, args, target, targetClass);
            return execute(invoker, operationContexts);
        }

        return invoker.invoke();
    }

    private Object execute(OperationInvoker invoker, OperationContexts contexts) throws Throwable {
        Object operationValue = null;
        final List<InvocationHandler> handlers = configuration.getInvocationHandlers();

        for (InvocationHandler handler : handlers) {
            Object value = handler.handleBefore(contexts, null);
            if (operationValue == null && value != null) {
                operationValue = value;
            }
        }

        if (operationValue != null) {
            return operationValue;
        }


        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invoker.invoke();
        } catch (Throwable e) {
            throwable = e;
        }

        for (int i = handlers.size() - 1; i >= 0; i--) {
            InvocationHandler handler = handlers.get(i);
            if (throwable == null) {
                handler.handleAfterReturning(contexts, returnValue);
            } else {
                handler.handleAfterThrowing(contexts, throwable);
            }

            handler.handleAfter(contexts, returnValue, throwable);
        }

        if (throwable != null) {
            if (throwable instanceof OperationException) {
                throw ((OperationException) throwable).getOriginal();
            }
            throw throwable;
        }

        return returnValue;

    }

    private class OperationContextsImpl implements OperationContexts {
        private final OperationConfiguration configuration;
        private final MultiValueMap<Class<? extends Operation>, OperationContext<? extends Operation>> contexts;

        public OperationContextsImpl(OperationConfiguration configuration, Collection<? extends Operation> operations, Method method, Object[] args, Object target, Class<?> targetClass) {
            this.configuration = configuration;
            this.contexts = new LinkedMultiValueMap<>(operations.size());
            for (Operation operation : operations) {
                final OperationContext<Operation> cacheOperationContext = getCacheOperationContext(operation, method, args, target, targetClass);
                this.contexts.add(operation.getClass(), cacheOperationContext);
            }
        }

        @Override
        public OperationConfiguration configuration() {
            return configuration;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <O extends Operation> Collection<OperationContext<? extends Operation>> get(Class<? extends O> operation) {
            return this.contexts.get(operation);
        }
    }

}
