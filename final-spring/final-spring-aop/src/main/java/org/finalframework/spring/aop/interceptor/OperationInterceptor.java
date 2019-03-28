package org.finalframework.spring.aop.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.OperationException;
import org.finalframework.spring.aop.OperationInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 21:01:13
 * @since 1.0
 */
public class OperationInterceptor extends OperationAspectSupport implements MethodInterceptor, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(OperationInterceptor.class);

    public OperationInterceptor(OperationConfiguration configuration) {
        super(configuration);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        OperationInvoker invoker = () -> {
            try {
                return invocation.proceed();
            } catch (Throwable ex) {
                logger.error("action invoker exception:", ex);
                throw new OperationException(ex);
            }
        };
        return execute(invoker, invocation.getThis(), method, invocation.getArguments());
    }
}
