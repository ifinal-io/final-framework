package org.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 */
public interface CacheInvocation<O extends CacheOperation, P, BEFORE, AFTER> {

    BEFORE before(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result);

    default AFTER after(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result, @Nullable Throwable throwable) {
        return throwable == null ? afterReturning(cache, context, result) : afterThrowing(cache, context, throwable);
    }

    default AFTER afterReturning(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result) {
        return null;
    }

    default AFTER afterThrowing(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @NonNull Throwable throwable) {
        return null;
    }

}
