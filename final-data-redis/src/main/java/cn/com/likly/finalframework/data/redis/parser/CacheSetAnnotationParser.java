package cn.com.likly.finalframework.data.redis.parser;

import cn.com.likly.finalframework.data.redis.CacheAnnotationParser;
import cn.com.likly.finalframework.data.redis.CacheOperation;
import cn.com.likly.finalframework.data.redis.annotation.CacheSet;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:11
 * @since 1.0
 */
public class CacheSetAnnotationParser implements CacheAnnotationParser<CacheSet> {
    @Override
    public CacheOperation parseCacheAnnotation(CacheSet cacheSet) {
        return DefaultCacheOperation.builder()
                .setOperation(CacheOperation.OperationType.SET)
                .setKey(cacheSet.key())
                .setCondition(cacheSet.condition())
                .setExpired(cacheSet.expired())
                .setTimeUnit(cacheSet.timeunit())
                .build();
    }
}
