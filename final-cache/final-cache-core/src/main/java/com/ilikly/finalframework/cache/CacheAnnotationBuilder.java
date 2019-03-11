package com.ilikly.finalframework.cache;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:23:17
 * @since 1.0
 */
public interface CacheAnnotationBuilder<A extends Annotation, O extends CacheOperation<A>> {
    @NonNull
    O build(@NonNull Class<?> type, @NonNull A ann);

    @NonNull
    O build(@NonNull Method method, @NonNull A ann);
}
