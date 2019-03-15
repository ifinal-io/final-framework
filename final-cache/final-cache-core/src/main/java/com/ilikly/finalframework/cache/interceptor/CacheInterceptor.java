package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheException;
import com.ilikly.finalframework.cache.CacheOperationInvoker;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 11:09:41
 * @see com.ilikly.finalframework.cache.annotation.Cacheable
 * @see com.ilikly.finalframework.cache.annotation.CachePut
 * @see com.ilikly.finalframework.cache.annotation.CacheDel
 * @since 1.0
 */
public class CacheInterceptor extends CacheAspectSupport implements MethodInterceptor, Serializable {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        CacheOperationInvoker invoker = () -> {
            try {
                return invocation.proceed();
            } catch (Throwable ex) {
                throw new CacheException(ex);
            }
        };
        return execute(invoker, invocation.getThis(), method, invocation.getArguments());
    }
}
