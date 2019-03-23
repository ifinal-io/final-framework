package org.finalframework.cache;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:23:17
 * @since 1.0
 */
public interface CacheAnnotationBuilder<A extends Annotation, O extends CacheOperation> {
    @NonNull
    O build(@NonNull Class<?> type, @NonNull A ann);

    @NonNull
    O build(@NonNull Method method, @NonNull A ann);

    @NonNull
    default O build(@NonNull Integer index, @NonNull Parameter parameter, @NonNull Type parameterType, @NonNull A ann) {
        return null;
    }
}
