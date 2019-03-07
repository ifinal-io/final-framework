package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.CacheAnnotationParser;
import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.cache.annotation.CacheDel;
import com.ilikly.finalframework.cache.annotation.CacheLock;
import com.ilikly.finalframework.cache.annotation.CachePut;
import com.ilikly.finalframework.cache.annotation.Cacheable;
import com.ilikly.finalframework.core.Assert;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:45:42
 * @since 1.0
 */
public class FinalCacheAnnotationParser implements CacheAnnotationParser, Serializable {
    private static final long serialVersionUID = -6963734575504018042L;


    private static final Set<Class<? extends Annotation>> CACHE_OPERATION_ANNOTATIONS = new LinkedHashSet<>(8);

    static {
        CACHE_OPERATION_ANNOTATIONS.add(CacheLock.class);
        CACHE_OPERATION_ANNOTATIONS.add(Cacheable.class);
        CACHE_OPERATION_ANNOTATIONS.add(CacheDel.class);
        CACHE_OPERATION_ANNOTATIONS.add(CachePut.class);
    }

    @Override
    public Collection<CacheOperation> parseCacheAnnotation(Class<?> type) {
        return parseCacheAnnotations(type);
    }

    @Override
    public Collection<CacheOperation> parseCacheAnnotation(Method method) {
        return parseCacheAnnotations(method);
    }

    private Collection<CacheOperation> parseCacheAnnotations(AnnotatedElement ae) {
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
    private Collection<CacheOperation> parseCacheAnnotations(AnnotatedElement ae, boolean localOnly) {
        final Collection<Annotation> anns = localOnly ?
                AnnotatedElementUtils.getAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS)
                : AnnotatedElementUtils.findAllMergedAnnotations(ae, CACHE_OPERATION_ANNOTATIONS);

        if (Assert.isEmpty(anns)) return null;

        final Collection<CacheOperation> ops = new ArrayList<>(1);
        anns.stream().filter(ann -> ann instanceof CacheLock).forEach(
                ann -> ops.add(parseCacheLockAnnotation(ae, (CacheLock) ann))
        );
        anns.stream().filter(ann -> ann instanceof Cacheable).forEach(
                ann -> ops.add(parseCacheableAnnotation(ae, (Cacheable) ann))
        );
        anns.stream().filter(ann -> ann instanceof CacheDel).forEach(
                ann -> ops.add(parseCacheDelAnnotation(ae, (CacheDel) ann))
        );
        anns.stream().filter(ann -> ann instanceof CachePut).forEach(
                ann -> ops.add(parseCachePutAnnotation(ae, (CachePut) ann))
        );

        return ops;

    }

    private CacheLockOperation parseCacheLockAnnotation(AnnotatedElement ae, CacheLock cacheable) {
        return CacheLockOperation.from(cacheable);
    }

    private CacheDelOperation parseCacheDelAnnotation(AnnotatedElement ae, CacheDel cacheDel) {
        return CacheDelOperation.from(cacheDel);
    }

    private CacheableOperation parseCacheableAnnotation(AnnotatedElement ae, Cacheable cacheable) {
        return CacheableOperation.from(cacheable);
    }

    private CachePutOperation parseCachePutAnnotation(AnnotatedElement ae, CachePut cachePut) {
        return CachePutOperation.from(cachePut);
    }


}
