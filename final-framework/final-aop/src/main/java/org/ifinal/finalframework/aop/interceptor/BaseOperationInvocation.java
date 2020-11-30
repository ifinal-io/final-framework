package org.ifinal.finalframework.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.ifinal.finalframework.aop.OperationConfiguration;
import org.ifinal.finalframework.aop.OperationContext;
import org.ifinal.finalframework.aop.OperationInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class BaseOperationInvocation extends OperationInvocationSupport implements OperationInvocation {

    private final MethodInvocation methodInvocation;

    public BaseOperationInvocation(OperationConfiguration configuration, MethodInvocation methodInvocation) {
        super(configuration);
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Method getMethod() {
        return methodInvocation.getMethod();
    }

    @Override
    public Object[] getArguments() {
        return methodInvocation.getArguments();
    }

    @Override
    public Object proceed() throws Throwable {
        return this.methodInvocation.proceed();
    }

    @Override
    public Object getThis() {
        return methodInvocation.getThis();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return methodInvocation.getStaticPart();
    }

    @Override
    public Collection<OperationContext> getOperationContexts() {
        return getOperationContexts(getThis(), getMethod(), getArguments());
    }
}
