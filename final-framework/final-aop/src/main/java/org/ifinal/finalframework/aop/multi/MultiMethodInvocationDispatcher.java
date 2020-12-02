package org.ifinal.finalframework.aop.multi;

import org.ifinal.finalframework.aop.InterceptorHandler;
import org.ifinal.finalframework.aop.InvocationContext;
import org.ifinal.finalframework.aop.MethodInvocationDispatcher;
import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class MultiMethodInvocationDispatcher<E, A> implements MethodInvocationDispatcher<Map<Class<? extends Annotation>, Collection<A>>> {

    private final MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers;

    public MultiMethodInvocationDispatcher(MultiValueMap<Class<? extends Annotation>, InterceptorHandler<E, A>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public Object before(@NonNull InvocationContext context, @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations) {

        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }


                for (A annotation : as) {
                    E executor = getExecutor(annotation);
                    Object value = handler.before(executor, context, annotation);
                    if (Objects.nonNull(value)) {
                        return value;
                    }
                }

            }
        }


        return null;
    }

    @Override
    public void afterReturning(@NonNull InvocationContext context, @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations, Object result) {
        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    E executor = getExecutor(annotation);
                    handler.afterReturning(executor, context, annotation, result);
                }

            }
        }
    }

    @Override
    public void afterThrowing(@NonNull InvocationContext context, @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations, @NonNull Throwable throwable) {
        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    E executor = getExecutor(annotation);
                    handler.afterThrowing(executor, context, annotation, throwable);
                }

            }
        }
    }

    @Override
    public void after(@NonNull InvocationContext context, @NonNull Map<Class<? extends Annotation>, Collection<A>> annotations, Object result, Throwable throwable) {
        for (Map.Entry<Class<? extends Annotation>, List<InterceptorHandler<E, A>>> entry : handlers.entrySet()) {
            List<InterceptorHandler<E, A>> annotationHandlers = entry.getValue();
            for (InterceptorHandler<E, A> handler : annotationHandlers) {
                Collection<A> as = annotations.get(entry.getKey());

                if (Objects.isNull(as) || as.isEmpty()) {
                    continue;
                }
                for (A annotation : as) {
                    E executor = getExecutor(annotation);
                    handler.after(executor, context, annotation, result, throwable);
                }

            }
        }
    }

    @NonNull
    protected abstract E getExecutor(A annotation);
}
