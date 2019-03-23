package org.finalframework.cache.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.finalframework.cache.CacheException;
import org.finalframework.cache.CacheOperationInvoker;
import org.finalframework.cache.annotation.CacheDel;
import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 11:09:41
 * @see Cacheable
 * @see CachePut
 * @see CacheDel
 * @since 1.0
 */
public class CacheInterceptor extends CacheAspectSupport implements MethodInterceptor, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        CacheOperationInvoker invoker = () -> {
            try {
                return invocation.proceed();
            } catch (Throwable ex) {
                logger.error("cache invoker exception:", ex);
                throw new CacheException(ex);
            }
        };
        return execute(invoker, invocation.getThis(), method, invocation.getArguments());
    }
}
