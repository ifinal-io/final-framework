package cn.com.likly.finalframework.cache;


import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 13:57
 * @since 1.0
 */
public interface CacheConfig {
    <A extends Annotation> CacheAnnotationParser<A> getCacheAnnotationParser(Class<A> annotationType);
}
