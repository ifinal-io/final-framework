package org.ifinal.finalframework.cache.handler;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.cache.Cache;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheInterceptorHandler extends InterceptorHandler<Cache, AnnotationAttributes> {

    @Nullable
    default JoinPoint point(AnnotationAttributes annotation) {
        return annotation.containsKey("point") ? annotation.getEnum("point") : null;
    }

    @Override
    default Object before(@NonNull Cache executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation) {
        if (JoinPoint.BEFORE == point(annotation)) {
            handle(executor, context, annotation, null, null);
        }
        return null;
    }

    @Override
    default void afterReturning(@NonNull Cache executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, Object result) {
        if (JoinPoint.AFTER_RETURNING == point(annotation)) {
            handle(executor, context, annotation, result, null);
        }
    }

    @Override
    default void afterThrowing(@NonNull Cache executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @NonNull Throwable throwable) {
        if (JoinPoint.AFTER_THROWING == point(annotation)) {
            handle(executor, context, annotation, null, throwable);
        }
    }

    @Override
    default void after(@NonNull Cache executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, Object result, Throwable throwable) {
        if (JoinPoint.AFTER == point(annotation)) {
            handle(executor, context, annotation, result, throwable);
        }
    }
}
