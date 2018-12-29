package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheAnnotationParser;
import com.ilikly.finalframework.cache.annotation.CacheDel;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:16:59
 * @since 1.0
 */
public class CacheDelAnnotationParser implements CacheAnnotationParser<CacheDel, CacheDelOperation> {
    @Override
    public CacheDelOperation parseCacheAnnotation(CacheDel ann) {
        return new CacheDelOperation(ann);
    }
}
