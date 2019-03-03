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
}
