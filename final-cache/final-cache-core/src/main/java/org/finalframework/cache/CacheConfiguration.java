package org.finalframework.cache;

import org.finalframework.cache.component.*;
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
 * @date 2019-03-11 10:35:22
 * @since 1.0
 */
public class CacheConfiguration {
    private static final Integer DEFAULT_INITIAL_SIZE = 8;
    private final Set<Class<? extends Annotation>> cacheOperationAnnotations = new CopyOnWriteArraySet<>();
    private final Map<Class<? extends Annotation>, CacheComponent> cacheComponents = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends Annotation>, CacheAnnotationBuilder> cacheAnnotationBuilders = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final Map<Class<? extends CacheInvocation>, CacheInvocation> cacheInvocations = new ConcurrentHashMap<>(DEFAULT_INITIAL_SIZE);
    private final List<CacheInvocationHandler> cacheInvocationHandlers = new CopyOnWriteArrayList<>();

    {
        registerCacheComponent(new CacheDelComponent());
        registerCacheComponent(new CacheableComponent());
        registerCacheComponent(new CacheValueComponent());
        registerCacheComponent(new CacheIncrementComponent());
        registerCacheComponent(new CachePutComponent());
        registerCacheComponent(new CacheDelComponent());
    }

    @SuppressWarnings("unchecked")
    public <T extends CacheComponent> void registerCacheComponent(T component) {
        cacheOperationAnnotations.add(component.annotation());
        cacheAnnotationBuilders.put(component.annotation(), component.builder());
        cacheInvocations.put(component.invocation().getClass(), component.invocation());
        cacheInvocationHandlers.add(component.handler());
        cacheComponents.put(component.annotation(), component);
    }

    public Set<Class<? extends Annotation>> getCacheOperationAnnotations() {
        return cacheOperationAnnotations;
    }

    public CacheAnnotationBuilder getCacheAnnotationBuilder(Class<? extends Annotation> ann) {
        return cacheAnnotationBuilders.get(ann);
    }

    public List<CacheInvocationHandler> getCacheInvocationHandlers() {
        return cacheInvocationHandlers;
    }


    public void registerCacheInvocation(CacheInvocation invocation) {
        cacheInvocations.put(invocation.getClass(), invocation);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T extends CacheInvocation> T getCacheInvocation(@NonNull Class<T> invocation) {
        return (T) cacheInvocations.get(invocation);
    }

}
