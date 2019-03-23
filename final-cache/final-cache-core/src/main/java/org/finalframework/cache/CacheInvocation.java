package org.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 调度
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 */
public interface CacheInvocation<O extends CacheOperation, P> {

    default Object before(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result) {
        return null;
    }

    default void afterReturning(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result) {
    }

    default void afterThrowing(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @NonNull Throwable throwable) {
    }

    default void after(@NonNull Cache cache, @NonNull CacheOperationContext<O, P> context, @Nullable Object result, @Nullable Throwable throwable) {
    }

}
