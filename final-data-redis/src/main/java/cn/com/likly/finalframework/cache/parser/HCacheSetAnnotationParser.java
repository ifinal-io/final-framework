package cn.com.likly.finalframework.cache.parser;

import cn.com.likly.finalframework.cache.CacheAnnotationParser;
import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.annotation.HCacheSet;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:11
 * @since 1.0
 */
public class HCacheSetAnnotationParser implements CacheAnnotationParser<HCacheSet> {
    @Override
    public CacheOperation parseCacheAnnotation(HCacheSet hCacheSet) {

        return DefaultCacheOperation.builder()
                .setOperation(CacheOperation.OperationType.HSET)
                .setKey(hCacheSet.key())
                .setField(hCacheSet.field())
                .setCondition(hCacheSet.condition())
                .setExpired(hCacheSet.expired())
                .setTimeUnit(hCacheSet.timeunit())
                .build();
    }
}
