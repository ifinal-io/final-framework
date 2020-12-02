package org.ifinal.finalframework.aop.multi;

import org.ifinal.finalframework.aop.AnnotationSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MultiAnnotationSource<E> implements AnnotationSource<Map<Class<? extends Annotation>, Collection<E>>> {
    private final Map<Object, Map<Class<? extends Annotation>, Collection<E>>> cache = new ConcurrentHashMap<>(1024);
    private final Map<Class<? extends Annotation>, AnnotationSource<Collection<E>>> sourceMap = new LinkedHashMap<>();

    public void addAnnotationSource(Class<? extends Annotation> annotationType, AnnotationSource<Collection<E>> source) {
        sourceMap.put(annotationType, source);
    }

    @Override
    public Map<Class<? extends Annotation>, Collection<E>> getAnnotations(Method method, Class<?> targetClass) {

        Object cacheKey = getCacheKey(method, targetClass);

        return cache.computeIfAbsent(cacheKey, key -> {
            Map<Class<? extends Annotation>, Collection<E>> map = new HashMap<>();

            for (Map.Entry<Class<? extends Annotation>, AnnotationSource<Collection<E>>> entry : sourceMap.entrySet()) {

                Collection<E> annotations = entry.getValue().getAnnotations(method, targetClass);

                if (Objects.isNull(annotations) || annotations.isEmpty()) {
                    continue;
                }

                map.put(entry.getKey(), annotations);

            }

            return map;
        });


    }
}
