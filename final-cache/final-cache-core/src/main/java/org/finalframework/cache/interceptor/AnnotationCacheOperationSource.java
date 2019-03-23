package org.finalframework.cache.interceptor;


import org.finalframework.cache.CacheAnnotationParser;
import org.finalframework.cache.CacheConfiguration;
import org.finalframework.cache.CacheOperation;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 10:36:06
 * @since 1.0
 */
public class AnnotationCacheOperationSource extends AbsCacheOperationSource implements Serializable {

    private static final long serialVersionUID = -1745346805308942300L;

    private final Set<CacheAnnotationParser> cacheAnnotationParsers;

    public AnnotationCacheOperationSource(CacheConfiguration cacheConfiguration) {
        this.cacheAnnotationParsers = Collections.singleton(new FinalCacheAnnotationParser(cacheConfiguration));
    }

    @Override
    protected Collection<CacheOperation> findCacheOperations(Method method) {
        return determineCacheOperations(parser -> parser.parseCacheAnnotation(method));
    }

    @Override
    protected Collection<CacheOperation> findCacheOperations(Class<?> clazz) {
        return determineCacheOperations(parser -> parser.parseCacheAnnotation(clazz));
    }

    @Nullable
    protected Collection<CacheOperation> determineCacheOperations(CacheOperationProvider provider) {
        Collection<CacheOperation> ops = null;

        for (CacheAnnotationParser annotationParser : cacheAnnotationParsers) {
            final Collection<CacheOperation> annOps = provider.getCacheOperations(annotationParser);
            if (annOps != null) {
                if (ops == null) {
                    ops = annOps;
                } else {
                    Collection<CacheOperation> combined = new ArrayList<>(ops.size() + annOps.size());
                    combined.addAll(ops);
                    combined.addAll(annOps);
                    ops = combined;
                }
            }
        }

        return ops;
    }

    @FunctionalInterface
    protected interface CacheOperationProvider {
        @Nullable
        Collection<CacheOperation> getCacheOperations(CacheAnnotationParser parser);
    }
}
