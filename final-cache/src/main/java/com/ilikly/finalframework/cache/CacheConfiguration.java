package com.ilikly.finalframework.cache;

import com.ilikly.finalframework.cache.interceptor.CacheOperationAspect;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:19:32
 * @since 1.0
 */
public interface CacheConfiguration {

    boolean isCacheAnnotation(Annotation annotation);

    <A extends Annotation, O extends CacheOperation<A>> void registerCacheAnnotationParser(A ann, CacheAnnotationParser<A, O> parser);

    <A extends Annotation, O extends CacheOperation<A>> CacheAnnotationParser<A, O> getCacheAnnotationParser(A ann);

    <A extends Annotation, O extends CacheOperation<A>> void registerCacheAspect(A ann, CacheOperationAspect<A, O> aspect);

    <A extends Annotation, O extends CacheOperation<A>> CacheOperationAspect<A, O> getCacheAspect(A ann);

}
