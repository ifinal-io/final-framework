package org.ifinal.finalframework.aop.single;

import org.ifinal.finalframework.annotation.aop.JoinPoint;
import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class SimpleInterceptorHandler<E>
        implements InterceptorHandler<E, AnnotationAttributes> {

    protected static JoinPoint point(AnnotationAttributes annotation) {
        return annotation.getEnum("point");
    }

    @Override
    public Object before(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation) {
        if (JoinPoint.BEFORE == point(annotation)) {
            return doBefore(executor, context, annotation);
        }
        return null;
    }

    @Override
    public void afterReturning(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @Nullable Object result) {
        if (JoinPoint.AFTER_RETURNING == point(annotation)) {
            doAfterReturning(executor, context, annotation, result);
        }
    }


    @Override
    public void afterThrowing(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @NonNull Throwable throwable) {
        if (JoinPoint.AFTER_THROWING == point(annotation)) {
            doAfterThrowing(executor, context, annotation, throwable);
        }
    }


    @Override
    public void after(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @Nullable Object result, @Nullable Throwable throwable) {
        if (JoinPoint.AFTER == point(annotation)) {
            doAfter(executor, context, annotation, result, throwable);
        }
    }

    protected Object doBefore(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation) {
        doHandle(executor, context, annotation, null, null);
        return null;
    }

    protected void doAfterReturning(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @Nullable Object result) {
        doHandle(executor, context, annotation, result, null);
    }

    protected void doAfterThrowing(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @NonNull Throwable throwable) {
        doHandle(executor, context, annotation, null, throwable);
    }

    protected void doAfter(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @Nullable Object result, @Nullable Throwable throwable) {
        doHandle(executor, context, annotation, result, throwable);
    }

    protected void doHandle(@NonNull E executor, @NonNull InvocationContext context, @NonNull AnnotationAttributes annotation, @Nullable Object result, @Nullable Throwable throwable) {

    }
}
