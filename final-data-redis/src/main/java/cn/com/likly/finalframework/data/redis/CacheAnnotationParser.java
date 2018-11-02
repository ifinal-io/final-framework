package cn.com.likly.finalframework.data.redis;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:07
 * @since 1.0
 */
public interface CacheAnnotationParser<A extends Annotation> {

    CacheOperation parseCacheAnnotation(A a);

}
