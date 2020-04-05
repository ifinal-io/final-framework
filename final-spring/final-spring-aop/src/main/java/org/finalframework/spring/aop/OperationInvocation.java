package org.finalframework.spring.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.spring.aop.interceptor.OperationInvocationSupport;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-04 23:13:36
 * @since 1.0
 */
public class OperationInvocation extends OperationInvocationSupport implements MethodInvocation {

    private final MethodInvocation methodInvocation;

    public OperationInvocation(OperationSource source, OperationInvocationHandler operationInvocationHandler, MethodInvocation methodInvocation) {
        super(source, operationInvocationHandler);
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
        return this.execute(this.methodInvocation);
    }

    @Override
    public Object getThis() {
        return methodInvocation.getThis();
    }

    @Override
    public AccessibleObject getStaticPart() {
        return methodInvocation.getStaticPart();
    }

}
