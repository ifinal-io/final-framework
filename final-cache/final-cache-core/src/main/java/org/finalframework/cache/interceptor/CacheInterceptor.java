package org.finalframework.cache.interceptor;


import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.interceptor.OperationInterceptor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 22:39:29
 * @since 1.0
 */
public class CacheInterceptor extends OperationInterceptor {

    public CacheInterceptor(OperationConfiguration configuration) {
        super(configuration);
    }
}
