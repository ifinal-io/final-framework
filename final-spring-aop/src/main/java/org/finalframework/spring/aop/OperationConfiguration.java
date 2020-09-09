

package org.finalframework.spring.aop;

import org.aopalliance.intercept.Interceptor;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.spring.aop.interceptor.AnnotationOperationSource;
import org.finalframework.spring.aop.interceptor.BaseOperationAnnotationFinder;
import org.finalframework.spring.aop.interceptor.BaseOperationInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.data.util.Lazy;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:07:56
 * @since 1.0
 */
@SpringComponent
@SuppressWarnings("unchecked")
public class OperationConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(OperationConfiguration.class);
    private static final Integer DEFAULT_INITIAL_SIZE = 8;
    private final Set<Class<? extends Annotation>> annotations = new CopyOnWriteArraySet<>();
    private final Map<Class<? extends Annotation>, OperationAnnotationFinder<? extends Annotation>> finders = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<Annotation>, OperationAnnotationBuilder<?, ?>> builders = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends OperationHandler>, OperationHandler<?, ?>> operationHandlers = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Executor>, Executor> executors = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Lazy<OperationInvocationHandler> invocationHandler;
    private final Lazy<OperationSource> operationSource;
    private final Lazy<Pointcut> pointcut;
    private final Lazy<Interceptor> interceptor;


    public OperationConfiguration(ObjectProvider<Executor> executorObjectProvider,
                                  ObjectProvider<OperationAnnotationBuilder<?, ?>> builderObjectProvider,
                                  ObjectProvider<OperationHandler<?, ?>> handlerObjectProvider) {
        executorObjectProvider.forEach(this::registerExecutor);
        builderObjectProvider.forEach(this::registerOperationAnnotationBuilder);
        handlerObjectProvider.forEach(this::registerOperationHandler);
        this.operationSource = Lazy.of(new AnnotationOperationSource(annotations, this));
        this.pointcut = Lazy.of(new OperationSourcePointcut(getOperationSource()));
        this.invocationHandler = Lazy.of(new BaseOperationInvocationHandler(this));
        this.interceptor = Lazy.of(new OperationInterceptor(this));
    }

    private void registerOperationAnnotationBuilder(OperationAnnotationBuilder<?, ?> builder) {
        final Class<Annotation> ann = (Class<Annotation>) findInterfaceParameterTypeClass(builder.getClass(), OperationAnnotationBuilder.class, 0);
        logger.debug("find annotation builder: ann=@{},builder={}", ann.getSimpleName(), builder.getClass().getSimpleName());
        this.annotations.add(ann);
        this.builders.put(ann, builder);
    }

    private void registerOperationHandler(OperationHandler<?, ?> handler) {
        final Class<? extends OperationHandler> handlerClass = handler.getClass();
        logger.debug("find operation handler: {}", handlerClass.getSimpleName());
        operationHandlers.put(handlerClass, handler);
    }

    private Class<?> findInterfaceParameterTypeClass(Class<?> target, Class<?> parameterizedInterface, int index) {
        final Type[] genericInterfaces = target.getGenericInterfaces();

        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType && parameterizedInterface.isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return (Class<? extends Annotation>) ((ParameterizedType) type).getActualTypeArguments()[index];
            }
        }

        throw new IllegalArgumentException("");
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

    public OperationAnnotationBuilder<Annotation, Operation> getOperationAnnotationBuilder(Class<? extends Annotation> ann) {
        return (OperationAnnotationBuilder<Annotation, Operation>) builders.get(ann);
    }

    public <T extends OperationHandler<?, ?>> T getHandler(@NonNull Class<T> invocation) {
        return (T) operationHandlers.get(invocation);
    }

    public Executor getExecutor(Operation operation) {
        return executors.get(operation.executor());
    }

    public OperationAnnotationFinder<? extends Annotation> getOperationAnnotationFinder(Class<? extends Annotation> annotation) {
        return finders.computeIfAbsent(annotation, key -> new BaseOperationAnnotationFinder<>(annotation));
    }
}
