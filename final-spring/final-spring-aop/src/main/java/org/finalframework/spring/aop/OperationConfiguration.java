package org.finalframework.spring.aop;

import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-26 10:07:56
 * @since 1.0
 */
public class OperationConfiguration {

    private static final Integer DEFAULT_INITIAL_SIZE = 8;
    private final Set<Class<? extends Annotation>> operationAnnotations = new CopyOnWriteArraySet<>();
    private final Map<Class<? extends Annotation>, OperationComponent> operationComponents = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Annotation>, OperationAnnotationBuilder> operationAnnotationBuilders = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends OperationHandler>, OperationHandler> operationHandlers = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Executor>, Executor> executors = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final List<Invocation> invocations = new CopyOnWriteArrayList<>();

    @SuppressWarnings("all")
    public void registerCacheComponent(OperationComponent component) {
        operationAnnotations.add(component.getAnnotation());
        operationAnnotationBuilders.put(component.getAnnotation(), component.getBuilder());
        operationHandlers.put(component.getHandler().getClass(), component.getHandler());
        invocations.add(component.getInvocation());
        operationComponents.put(component.getAnnotation(), component);
    }

    public void registerExecutor(Class<? extends Executor> clazz, Executor executor) {
        executors.put(clazz, executor);
    }


    public Set<Class<? extends Annotation>> getOperationAnnotations() {
        return operationAnnotations;
    }

    public OperationAnnotationBuilder getOperationAnnotationBuilder(Class<? extends Annotation> ann) {
        return operationAnnotationBuilders.get(ann);
    }

    public <T extends OperationHandler> T getHandler(@NonNull Class<T> invocation) {
        return (T) operationHandlers.get(invocation);
    }

    public List<Invocation> getInvocations() {
        return invocations;
    }

    public OperationHandler getHandler(Operation operation) {
        return operationHandlers.get(operation.handler());
    }

    public Executor getExecutor(Operation operation) {
        return executors.get(operation.executor());
    }
}
