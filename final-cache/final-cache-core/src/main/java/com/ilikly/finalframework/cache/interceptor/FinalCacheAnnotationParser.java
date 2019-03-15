package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheAnnotationBuilder;
import com.ilikly.finalframework.cache.CacheAnnotationParser;
import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.cache.annotation.CacheDel;
import com.ilikly.finalframework.cache.annotation.CacheLock;
import com.ilikly.finalframework.cache.annotation.CachePut;
import com.ilikly.finalframework.cache.annotation.Cacheable;
import com.ilikly.finalframework.cache.builder.CacheDelAnnotationBuilder;
import com.ilikly.finalframework.cache.builder.CacheLockAnnotationBuilder;
import com.ilikly.finalframework.cache.builder.CachePutAnnotationBuilder;
import com.ilikly.finalframework.cache.builder.CacheableAnnotationBuilder;
import com.ilikly.finalframework.core.Assert;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:45:42
 * @since 1.0
 */
public class FinalCacheAnnotationParser implements CacheAnnotationParser, Serializable {
    private static final long serialVersionUID = -6963734575504018042L;
    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(8);

    private static final Map<Class<? extends Annotation>, CacheAnnotationBuilder> cacheAnnotationBuilders = new LinkedHashMap<>();


    static {
        CACHE_OPERATION_ANNOTATIONS.add(CacheLock.class);
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheDel.class);
        CACHE_OPERATION_ANNOTATIONS.add(CachePut.class);

        cacheAnnotationBuilders.put(CacheLock.class, new CacheLockAnnotationBuilder());
        cacheAnnotationBuilders.put(Cacheable.class, new CacheableAnnotationBuilder());
        cacheAnnotationBuilders.put(CacheDel.class, new CacheDelAnnotationBuilder());
        cacheAnnotationBuilders.put(CachePut.class, new CachePutAnnotationBuilder());
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
        final Collection<CacheOperation> ops = parseCacheAnnotations(ae, false);
        if (ops != null && ops.size() > 1) {
            final Collection<CacheOperation> localOps = parseCacheAnnotations(ae, true);
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
                AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS)
                : AnnotatedElementUtils.findAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS);

        if (Assert.isEmpty(anns)) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);

        CACHE_OPERATION_ANNOTATIONS.forEach(an -> {
            final CacheAnnotationBuilder builder = cacheAnnotationBuilders.get(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(ae, ann)));
        });

        return ops;

    }


    private Collection<CacheOperation> parseCacheAnnotations(Method ae) {
        final Collection<CacheOperation> ops = parseCacheAnnotations(ae, false);
        if (ops != null && ops.size() > 1) {
            final Collection<CacheOperation> localOps = parseCacheAnnotations(ae, true);
            if (localOps != null) {
                return localOps;
            }
        }
        return ops;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private Collection<CacheOperation> parseCacheAnnotations(Method ae, boolean localOnly) {
        final Collection<Annotation> anns = localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS)
                : AnnotatedElementUtils.findAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS);

        if (Assert.isEmpty(anns)) {
            return null;
        }

        final Collection<CacheOperation> ops = new ArrayList<>(1);

        CACHE_OPERATION_ANNOTATIONS.forEach(an -> {
            final CacheAnnotationBuilder builder = cacheAnnotationBuilders.get(an);
            anns.stream().filter(ann -> ann.annotationType() == an)
                    .forEach(ann -> ops.add(builder.build(ae, ann)));
        });

        return ops;

    }


}
