package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.CacheAnnotationParser;
import cn.com.likly.finalframework.cache.annotation.CacheSet;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:16:59
 * @since 1.0
 */
public class CacheSetAnnotationParser implements CacheAnnotationParser<CacheSet, CacheSetOperation> {
    @Override
    public CacheSetOperation parseCacheAnnotation(CacheSet ann) {
        return new CacheSetOperation(ann);
    }
}
