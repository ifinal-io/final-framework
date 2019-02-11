package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.annotation.CacheDel;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheDelOperation extends AbsCacheOperation<CacheDel> {

    private CacheDelOperation(Builder builder) {
        super(builder);
    }

    public static CacheDelOperation from(CacheDel cacheDel) {
        return new Builder()
                .keyFormat(cacheDel.keyPattern())
                .keys(cacheDel.keys())
                .fieldFormat(cacheDel.fieldPattern())
                .fields(cacheDel.fields())
                .condition(cacheDel.condition())
                .build();
    }

    @Override
    public String expire() {
        throw new UnsupportedOperationException("cache del operation no support expire");
    }

    @Override
    public Long ttl() {
        throw new UnsupportedOperationException("cache del operation no support ttl");
    }

    @Override
    public TimeUnit timeUnit() {
        throw new UnsupportedOperationException("cache del operation no support timeUnit");
    }

    private static class Builder extends AbsCacheOperation.Builder<CacheDelOperation> {
        @Override
        public CacheDelOperation build() {
            return new CacheDelOperation(this);
        }
    }
}
