package org.finalframework.spring.aop;

import lombok.Setter;
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
    private final Map<Class<? extends Invocation>, Invocation> invocations = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final List<InvocationHandler> invocationHandlers = new CopyOnWriteArrayList<>();
    @Setter
    private OperationExecutor executor;


    @SuppressWarnings("all")
    public void registerCacheComponent(OperationComponent component) {
        operationAnnotations.add(component.annotation());
        operationAnnotationBuilders.put(component.annotation(), component.builder());
        invocations.put(component.invocation().getClass(), component.invocation());
        invocationHandlers.add(component.handler());
        operationComponents.put(component.annotation(), component);
    }


    public Set<Class<? extends Annotation>> getOperationAnnotations() {
        return operationAnnotations;
    }

    public OperationAnnotationBuilder getOperationAnnotationBuilder(Class<? extends Annotation> ann) {
        return operationAnnotationBuilders.get(ann);
    }

    public <T extends Invocation> T getInvocation(@NonNull Class<T> invocation) {
        return (T) invocations.get(invocation);
    }

    public List<InvocationHandler> getInvocationHandlers() {
        return invocationHandlers;
    }

    public OperationExecutor getExecutor(Operation operation) {
        return executor;
    }
}
