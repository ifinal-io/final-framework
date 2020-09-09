

package org.finalframework.spring.aop.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.OperationContext;
import org.finalframework.spring.aop.OperationInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-04 23:13:36
 * @since 1.0
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
    public Collection<OperationContext<Operation>> getOperationContexts() {
        return getOperationContexts(getThis(), getMethod(), getArguments());
    }
}
