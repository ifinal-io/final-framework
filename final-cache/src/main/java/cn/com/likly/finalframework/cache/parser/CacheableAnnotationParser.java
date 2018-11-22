package cn.com.likly.finalframework.cache.parser;

import cn.com.likly.finalframework.cache.CacheAnnotationParser;
import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.annotation.Cacheable;
import cn.com.likly.finalframework.cache.operation.CacheableOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:16:59
 * @since 1.0
 */
public class CacheableAnnotationParser implements CacheAnnotationParser<Cacheable> {
    @Override
    public CacheOperation parseCacheAnnotation(Cacheable ann) {
        return new CacheableOperation(ann);
    }
}
