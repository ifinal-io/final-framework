package org.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:23:59
 * @since 1.0
 */
public interface CacheInvocationHandler<BEFORE, AFTER> {

    BEFORE handleBefore(@NonNull CacheOperationContexts contexts, @Nullable Object result);

    AFTER handleAfter(@NonNull CacheOperationContexts contexts, @Nullable Object result, @Nullable Throwable throwable);

}
