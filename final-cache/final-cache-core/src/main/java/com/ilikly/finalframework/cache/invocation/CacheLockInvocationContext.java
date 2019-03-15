package com.ilikly.finalframework.cache.invocation;

import com.ilikly.finalframework.cache.CacheInvocationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 00:59:48
 * @since 1.0
 */
public interface CacheLockInvocationContext extends CacheInvocationContext {
    boolean lock();

    Object key();

    Object value();
}
