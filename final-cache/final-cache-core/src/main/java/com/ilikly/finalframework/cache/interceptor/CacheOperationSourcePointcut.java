package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheOperationPointCut;
import com.ilikly.finalframework.cache.CacheOperationSource;
import com.ilikly.finalframework.core.Assert;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 13:19:55
 * @since 1.0
 */
public abstract class CacheOperationSourcePointcut extends StaticMethodMatcherPointcut implements CacheOperationPointCut, Serializable {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        final CacheOperationSource source = getCacheOperationSource();
        return source != null && Assert.nonEmpty(source.getCacheOperations(method, targetClass));
    }

    protected abstract CacheOperationSource getCacheOperationSource();
}
