package cn.com.likly.finalframework.data.redis.parser;

import cn.com.likly.finalframework.data.redis.CacheAnnotationParser;
import cn.com.likly.finalframework.data.redis.CacheOperation;
import cn.com.likly.finalframework.data.redis.annotation.HCacheDel;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:11
 * @since 1.0
 */
public class HCacheDelAnnotationParser implements CacheAnnotationParser<HCacheDel> {
    @Override
    public CacheOperation parseCacheAnnotation(HCacheDel hCacheDel) {

        return DefaultCacheOperation.builder()
                .setOperation(CacheOperation.OperationType.HDEL)
                .setKey(hCacheDel.key())
                .setField(hCacheDel.field())
                .setCondition(hCacheDel.condition())
                .build();
    }
}
