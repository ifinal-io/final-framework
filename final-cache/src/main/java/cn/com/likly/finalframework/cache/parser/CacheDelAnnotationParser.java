package cn.com.likly.finalframework.cache.parser;

import cn.com.likly.finalframework.cache.CacheAnnotationParser;
import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.annotation.CacheDel;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:11
 * @since 1.0
 */
public class CacheDelAnnotationParser implements CacheAnnotationParser<CacheDel> {
    @Override
    public CacheOperation parseCacheAnnotation(CacheDel cacheDel) {
        return DefaultCacheOperation.builder()
                .setOperation(CacheOperation.OperationType.DEL)
                .setKey(cacheDel.key())
                .setCondition(cacheDel.condition())
                .build();
    }
}
