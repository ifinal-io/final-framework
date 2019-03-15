package com.ilikly.finalframework.cache.invocation;

import com.ilikly.finalframework.cache.CacheInvocationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 00:42:33
 * @since 1.0
 */
public interface CacheableInvocationContext extends CacheInvocationContext {

    @NonNull
    Object key();

    @Nullable
    Object field();
}
