package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.annotation.CacheSet;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheSetOperation extends AbsCacheOperation<CacheSet> {

    private CacheSetOperation(Builder builder) {
        super(builder);
    }

    public static CacheSetOperation from(CacheSet cacheSet) {
        return new Builder()
                .key(cacheSet.key())
                .field(cacheSet.field())
                .delimiter(cacheSet.delimiter())
                .condition(cacheSet.condition())
                .expire(cacheSet.expire())
                .ttl(cacheSet.ttl())
                .timeUnit(cacheSet.timeunit())
                .build();
    }

    private static class Builder extends AbsCacheOperation.Builder<CacheSetOperation> {

        @Override
        public CacheSetOperation build() {
            return new CacheSetOperation(this);
        }
    }
}
