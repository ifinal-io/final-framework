package org.finalframework.cache;

import org.finalframework.cache.interceptor.CacheOperationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 00:14:41
 * @since 1.0
 */
public interface CacheOperationContext<O extends CacheOperation, IC> {

    @NonNull
    O operation();

    @NonNull
    CacheOperationMetadata<O> metadata();

    @NonNull
    Object target();

    @NonNull
    Method method();

    @NonNull
    Class<?> view();

    @Nullable
    Object[] args();

    Class<?> returnType();

    Type genericReturnType();


    IC invocation();

    void invocation(IC context);

}