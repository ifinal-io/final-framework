package org.ifinal.finalframework.aop.simple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.ifinal.finalframework.aop.AnnotationSource;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class SimpleAnnotationSource implements AnnotationSource<Boolean> {

    private final Map<Object, Boolean> cache = new ConcurrentHashMap<>(1024);

    private final SimpleAnnotationFinder finder;

    public SimpleAnnotationSource(final Collection<Class<? extends Annotation>> annotationTypes) {

        this.finder = new SimpleAnnotationFinder(annotationTypes);
    }

    @Override
    public Boolean getAnnotations(final Method method, final Class<?> targetClass) {

        final Object cacheKey = getCacheKey(method, targetClass);
        return cache.computeIfAbsent(cacheKey, key -> {

            if (Boolean.TRUE.equals(finder.findAnnotations(method))) {
                return true;
            }

            if (Boolean.TRUE.equals(finder.findAnnotations(targetClass))) {
                return true;
            }

            for (Parameter parameter : method.getParameters()) {
                if (Boolean.TRUE.equals(finder.findAnnotations(parameter))) {
                    return true;
                }
            }

            return false;
        });
    }

}
