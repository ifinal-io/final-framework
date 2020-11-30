package org.ifinal.finalframework.aop;

import org.aopalliance.intercept.Interceptor;
import org.ifinal.finalframework.aop.interceptor.AnnotationOperationSource;
import org.ifinal.finalframework.aop.interceptor.BaseOperationAnnotationFinder;
import org.ifinal.finalframework.aop.interceptor.BaseOperationInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class OperationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(OperationConfiguration.class);
    private static final Integer DEFAULT_INITIAL_SIZE = 8;
    private final Set<Class<? extends Annotation>> annotations = new CopyOnWriteArraySet<>();
    private final Map<Class<? extends Annotation>, OperationAnnotationFinder<? extends Annotation>> finders = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Annotation>, OperationHandler<?, ? extends Annotation>> operationHandlers = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Executor>, Executor> executors = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Lazy<OperationInvocationHandler> invocationHandler;
    private final Lazy<OperationSource> operationSource;
    private final Lazy<Pointcut> pointcut;
    private final Lazy<Interceptor> interceptor;


    public OperationConfiguration(ObjectProvider<Executor> executorObjectProvider,
                                  ObjectProvider<OperationHandler<?, ? extends Annotation>> handlerObjectProvider) {
        executorObjectProvider.forEach(this::registerExecutor);
        handlerObjectProvider.forEach(this::registerOperationHandler);
        this.operationSource = Lazy.of(new AnnotationOperationSource(annotations, this));
        this.pointcut = Lazy.of(new OperationSourcePointcut(getOperationSource()));
        this.invocationHandler = Lazy.of(new BaseOperationInvocationHandler(this));
        this.interceptor = Lazy.of(new OperationInterceptor(this));
    }


    private void registerOperationHandler(OperationHandler<?, ? extends Annotation> handler) {
        final Class<? extends OperationHandler> handlerClass = handler.getClass();
        logger.debug("find operation handler: {}", handlerClass.getSimpleName());
//        operationHandlers.put(handlerClass, handler);
    }

    private void registerExecutor(Executor executor) {
        executors.put(executor.getClass(), executor);
        if (AnnotatedElementUtils.hasAnnotation(executor.getClass(), Primary.class)) {
            for (Class<?> interfaceClass : executor.getClass().getInterfaces()) {
                if (Executor.class.isAssignableFrom(interfaceClass)) {
                    executors.put((Class<? extends Executor>) interfaceClass, executor);
                }
            }
        }

    }


    public Set<Class<? extends Annotation>> getAnnotations() {
        return annotations;
    }

    public OperationSource getOperationSource() {
        return this.operationSource.get();
    }

    public Pointcut getPointcut() {
        return this.pointcut.get();
    }

    public OperationInvocationHandler getInvocationHandler() {
        return invocationHandler.get();
    }

    public Interceptor getInterceptor() {
        return interceptor.get();
    }

    public <T extends OperationHandler<?, ? extends Annotation>> T getHandler(@NonNull Class<? extends Annotation> invocation) {
        return (T) operationHandlers.get(invocation);
    }

    public Executor getExecutor(Class<? extends Executor> executor) {
        return executors.get(executor);
    }

    public <T extends Annotation> OperationAnnotationFinder<T> getOperationAnnotationFinder(Class<T> annotation) {
        return (OperationAnnotationFinder<T>) finders.computeIfAbsent(annotation, key -> new BaseOperationAnnotationFinder<>(annotation));
    }
}
