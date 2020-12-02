package org.ifinal.finalframework.aop.multi;

import org.aopalliance.intercept.MethodInvocation;
import org.ifinal.finalframework.aop.*;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.expression.AnnotatedElementKey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationMethodInterceptor<A> implements AnnotationMethodInterceptor<Map<Class<? extends Annotation>, Collection<A>>> {

    private final Map<AnnotatedElementKey, MethodMetadata> metadataCache = new ConcurrentHashMap<>(1024);

    private final AnnotationSource<Map<Class<? extends Annotation>, Collection<A>>> source;
    private final MethodInvocationDispatcher<Map<Class<? extends Annotation>, Collection<A>>> dispatcher;

    public MultiAnnotationMethodInterceptor(AnnotationSource<Map<Class<? extends Annotation>, Collection<A>>> source,
                                            MethodInvocationDispatcher<Map<Class<? extends Annotation>, Collection<A>>> dispatcher) {
        this.source = source;
        this.dispatcher = dispatcher;
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<A>> findAnnotations(Method method, Class clazz) {
        return source.getAnnotations(method, clazz);
    }

    @Override
    public Object invoke(MethodInvocation invocation, Map<Class<? extends Annotation>, Collection<A>> annotations) throws Throwable {

        if (Asserts.isEmpty(annotations)) {
            return invocation.proceed();
        }

        Class<?> targetClass = getTargetClass(invocation.getThis());
        MethodMetadata metadata = getOperationMetadata(invocation.getMethod(), targetClass);

        InvocationContext context = new DefaultInvocationContext(metadata, invocation.getThis(), invocation.getArguments());

        Object operationValue = dispatcher.before(context, annotations);
        if (operationValue != null) {
            return operationValue;
        }

        Object returnValue = null;
        Throwable throwable = null;

        try {
            returnValue = invocation.proceed();
        } catch (Throwable e) {
            throwable = e;
        }

        if (throwable == null) {
            dispatcher.afterReturning(context, annotations, returnValue);
        } else {
            dispatcher.afterThrowing(context, annotations, throwable);
        }

        dispatcher.after(context, annotations, returnValue, throwable);

        if (throwable != null) {
            throw throwable;
        }

        return returnValue;
    }


    private MethodMetadata getOperationMetadata(Method method, Class<?> targetClass) {
        final AnnotatedElementKey cacheKey = new AnnotatedElementKey(method, targetClass);
        return this.metadataCache.computeIfAbsent(cacheKey, key -> new MethodMetadata(method, targetClass));
    }

    private Class<?> getTargetClass(Object target) {
        return AopProxyUtils.ultimateTargetClass(target);
    }

}
