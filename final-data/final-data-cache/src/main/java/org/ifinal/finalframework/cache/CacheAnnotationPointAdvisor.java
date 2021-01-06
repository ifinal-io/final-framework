package org.ifinal.finalframework.cache;

import javax.annotation.Resource;
import org.ifinal.finalframework.annotation.cache.Cache;
import org.ifinal.finalframework.annotation.cache.CacheDel;
import org.ifinal.finalframework.annotation.cache.CacheIncrement;
import org.ifinal.finalframework.annotation.cache.CacheLock;
import org.ifinal.finalframework.annotation.cache.CachePut;
import org.ifinal.finalframework.annotation.cache.CacheValue;
import org.ifinal.finalframework.annotation.cache.Cacheable;
import org.ifinal.finalframework.aop.AnnotationAttributesAnnotationBuilder;
import org.ifinal.finalframework.aop.multi.MultiAnnotationPointAdvisor;
import org.ifinal.finalframework.cache.handler.CacheDelInterceptorHandler;
import org.ifinal.finalframework.cache.handler.CacheIncrementInterceptorHandler;
import org.ifinal.finalframework.cache.handler.CacheLockInterceptorHandler;
import org.ifinal.finalframework.cache.handler.CachePutInterceptorHandler;
import org.ifinal.finalframework.cache.handler.CacheValueInterceptorHandler;
import org.ifinal.finalframework.cache.handler.CacheableInterceptorHandler;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CacheAnnotationPointAdvisor extends MultiAnnotationPointAdvisor<AnnotationAttributes, Cache> {

    @Resource
    private RedisCache redisCache;

    public CacheAnnotationPointAdvisor() {

        this.addAnnotation(CacheLock.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CacheLockInterceptorHandler());
        this.addAnnotation(Cacheable.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CacheableInterceptorHandler());
        this.addAnnotation(CachePut.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CachePutInterceptorHandler());
        this.addAnnotation(CacheDel.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CacheDelInterceptorHandler());
        this.addAnnotation(CacheIncrement.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CacheIncrementInterceptorHandler());
        this.addAnnotation(CacheValue.class, new AnnotationAttributesAnnotationBuilder<>(),
            new CacheValueInterceptorHandler());

    }

    @Override
    @NonNull
    protected Cache getExecutor(final AnnotationAttributes annotation) {

        return redisCache;
    }

}
