package cn.com.likly.finalframework.cache;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:19:32
 * @since 1.0
 */
public interface CacheConfigurtion {

    boolean isCacheAnnotation(Annotation annotation);

    <A extends Annotation> void registerCacheAnnotationParser(A ann, CacheAnnotationParser<A> parser);

    <A extends Annotation> CacheAnnotationParser<A> getCacheAnnotationParser(A ann);

    <A extends Annotation> void registerCacheAspect(A ann, CacheAspect aspect);

    <A extends Annotation> CacheAspect<A> getCacheAspect(A ann);

}
