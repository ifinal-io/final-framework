package org.ifinal.finalframework.aop;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.ifinal.finalframework.util.Asserts;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface AnnotationMethodInterceptor<R> extends MethodInterceptor {

    R findAnnotations(final Method method, final Class<?> clazz);

    @Override
    default Object invoke(final MethodInvocation invocation) throws Throwable {

        R annotations = findAnnotations(invocation.getMethod(), invocation.getThis().getClass());

        if (Asserts.isEmpty(annotations)) {
            return invocation.proceed();
        }

        return invoke(invocation, annotations);
    }

    Object invoke(final MethodInvocation invocation, final R annotations) throws Throwable;

}
