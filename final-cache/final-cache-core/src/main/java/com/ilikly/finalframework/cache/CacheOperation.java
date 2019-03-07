package com.ilikly.finalframework.cache;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:12:47
 * @since 1.0
 */
public interface CacheOperation<A extends Annotation> {
    @NonNull
    String[] key();

    @Nullable
    String[] field();

    @Nullable
    String value();

    @NonNull
    String delimiter();

    @Nullable
    String condition();

    @Nullable
    String expire();

    @Nullable
    Long ttl();

    @NonNull
    TimeUnit timeUnit();

    @NonNull
    Integer retry();

    Long sleep();

    interface Builder<O extends CacheOperation, B extends Builder> extends com.ilikly.finalframework.core.Builder<O> {
        B key(@NonNull String[] key);

        B field(@Nullable String[] field);

        B value(@Nullable String result);

        B delimiter(@NonNull String delimiter);

        B condition(@Nullable String condition);

        B expire(@Nullable String expire);

        B ttl(@Nullable Long ttl);

        B timeUnit(@NonNull TimeUnit timeUnit);

        B retry(@Nullable Integer retry);

        B sleep(@Nullable Long sleep);
    }
}
