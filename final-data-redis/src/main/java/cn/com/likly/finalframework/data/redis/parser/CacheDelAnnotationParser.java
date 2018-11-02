package cn.com.likly.finalframework.data.redis.parser;

import cn.com.likly.finalframework.data.redis.CacheAnnotationParser;
import cn.com.likly.finalframework.data.redis.CacheOperation;
import cn.com.likly.finalframework.data.redis.annotation.CacheDel;

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
