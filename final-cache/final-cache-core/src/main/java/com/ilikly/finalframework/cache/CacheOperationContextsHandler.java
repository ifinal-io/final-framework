package com.ilikly.finalframework.cache;

import com.ilikly.finalframework.cache.annotation.enums.CacheInvocationTime;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:23:59
 * @since 1.0
 */
public interface CacheOperationContextsHandler<T> {

    T handle(@NonNull CacheOperationContexts contexts, @Nullable Object result, @NonNull CacheInvocationTime invocationTime);

}
