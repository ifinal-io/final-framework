package org.finalframework.cache.interceptor;


import org.finalframework.cache.CacheAnnotationBuilder;
import org.finalframework.cache.CacheAnnotationParser;
import org.finalframework.cache.CacheConfiguration;
import org.finalframework.cache.CacheOperation;
import org.finalframework.core.Assert;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:45:42
 * @since 1.0
 */
public class FinalCacheAnnotationParser implements CacheAnnotationParser, Serializable {
    private static final long serialVersionUID = -6963734575504018042L;
    private final CacheConfiguration cacheConfiguration;

    public FinalCacheAnnotationParser(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
    }

    @Override
    public Collection<CacheOperation> parseCacheAnnotation(Class<?> type) {
        return parseCacheAnnotations(type);
    }

    @Override
    public Collection<CacheOperation> parseCacheAnnotation(Method method) {
        return parseCacheAnnotations(method);
    }

    private Collection<CacheOperation> parseCacheAnnotations(Class<?> ae) {
        Collection<CacheOperation> ops = parseCacheAnnotations(ae, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            Collection<CacheOperation> localOps = parseCacheAnnotations(ae, true);
            if (localOps != null) {
                return localOps;
            }
        }
        return ops;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Collection<CacheOperation> parseCacheAnnotations(Class<?> ae, boolean localOnly) {
        final Collection<Annotation> anns = localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, cacheConfiguration.getCacheOperationAnnotations())
                : AnnotatedElementUtils.findAllMergedAnnotations(ae, cacheConfiguration.getCacheOperationAnnotations());
        if (Assert.isEmpty(anns)) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);

        cacheConfiguration.getCacheOperationAnnotations().forEach(an -> {
            final CacheAnnotationBuilder builder = cacheConfiguration.getCacheAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(ae, ann)));
        });

        return ops;

    }


    private Collection<CacheOperation> parseCacheAnnotations(Method method) {
        List<CacheOperation> result = new ArrayList<>();

        final Collection<CacheOperation> ops = parseCacheAnnotations(method, false);
        if (ops != null && ops.size() > 1) {
            // More than one operation found -> local declarations override interface-declared ones...
            final Collection<CacheOperation> localOps = parseCacheAnnotations(method, true);
            if (localOps != null) {
                result.addAll(localOps);
            }
        } else if (ops != null) {
            result.addAll(ops);
        }

        final Parameter[] parameters = method.getParameters();
        final Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            final Collection<CacheOperation> cacheOperations = parseCacheAnnotations(i, parameters[i], genericParameterTypes[i], false);
            if (cacheOperations != null && cacheOperations.size() > 1) {
                final Collection<CacheOperation> localOps = parseCacheAnnotations(i, parameters[i], genericParameterTypes[i], true);
                if (localOps != null) {
                    result.addAll(localOps);
                }
            } else if (cacheOperations != null) {
                result.addAll(cacheOperations);
            }
        }
        return result;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Collection<CacheOperation> parseCacheAnnotations(Method ae, boolean localOnly) {
        final Set<Class<? extends Annotation>> cacheOperationAnnotations = cacheConfiguration.getCacheOperationAnnotations();
        final Collection<Annotation> anns = localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, cacheOperationAnnotations)
                : AnnotatedElementUtils.findAllMergedAnnotations(ae, cacheOperationAnnotations);
        if (Assert.isEmpty(anns)) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);

        cacheOperationAnnotations.forEach(an -> {
            final CacheAnnotationBuilder builder = cacheConfiguration.getCacheAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(ae, ann)));
        });

        return ops;

    }

    private Collection<CacheOperation> parseCacheAnnotations(Integer index, Parameter parameter, Type parameterType, boolean localOnly) {
        final Set<Class<? extends Annotation>> cacheOperationAnnotations = cacheConfiguration.getCacheOperationAnnotations();
        final Collection<Annotation> anns = localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(parameter, cacheOperationAnnotations)
                : AnnotatedElementUtils.findAllMergedAnnotations(parameter, cacheOperationAnnotations);
        if (Assert.isEmpty(anns)) {
            return null;
        }
        final Collection<CacheOperation> ops = new ArrayList<>(1);
        cacheOperationAnnotations.forEach(an -> {
            final CacheAnnotationBuilder builder = cacheConfiguration.getCacheAnnotationBuilder(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(index, parameter, parameterType, ann)));
        });
        return ops;
    }


}
