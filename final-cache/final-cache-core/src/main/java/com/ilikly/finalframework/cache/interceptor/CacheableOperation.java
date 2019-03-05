package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.annotation.Cacheable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheableOperation extends AbsCacheOperation<Cacheable> {

    private CacheableOperation(Builder builder) {
        super(builder);
    }

    public static CacheableOperation from(Cacheable cacheable) {
        return new Builder()
                .key(cacheable.key())
                .field(cacheable.field())
                .delimiter(cacheable.delimiter())
                .condition(cacheable.condition())
                .expire(cacheable.expire())
                .ttl(cacheable.ttl())
                .timeUnit(cacheable.timeunit())
                .build();
    }

    private static class Builder extends AbsCacheOperation.Builder<CacheableOperation> {

        @Override
        public CacheableOperation build() {
            return new CacheableOperation(this);
        }
    }
}
