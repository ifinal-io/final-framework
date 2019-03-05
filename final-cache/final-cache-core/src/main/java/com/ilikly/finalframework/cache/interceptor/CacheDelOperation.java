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

    private final boolean beforeInvocation;

    private CacheDelOperation(Builder builder) {
        super(builder);
        this.beforeInvocation = builder.beforeInvocation;
    }

    public static CacheDelOperation from(CacheDel cacheDel) {
        return new Builder()
                .beforeInvocation(cacheDel.beforeInvocation())
                .key(cacheDel.key())
                .field(cacheDel.field())
                .delimiter(cacheDel.delimiter())
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

    public boolean isBeforeInvocation() {
        return beforeInvocation;
    }

    private static class Builder extends AbsCacheOperation.Builder<CacheDelOperation> {
        private boolean beforeInvocation;

        public Builder beforeInvocation(boolean beforeInvocation) {
            this.beforeInvocation = beforeInvocation;
            return this;
        }

        @Override
        public CacheDelOperation build() {
            return new CacheDelOperation(this);
        }
    }
}
