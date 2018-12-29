package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheAnnotationParser;
import com.ilikly.finalframework.cache.annotation.CacheSet;

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
