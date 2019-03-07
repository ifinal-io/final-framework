package com.ilikly.finalframework.cache.interceptor;


import com.ilikly.finalframework.cache.annotation.CacheLock;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 22:22:10
 * @since 1.0
 */
public class CacheLockOperation extends AbsCacheOperation<CacheLock> {

    private CacheLockOperation(Builder builder) {
        super(builder);
    }

    public static CacheLockOperation from(CacheLock cacheLock) {
        return new Builder()
                .key(cacheLock.key())
                .value(cacheLock.value())
                .delimiter(cacheLock.delimiter())
                .ttl(cacheLock.ttl())
                .timeUnit(cacheLock.timeunit())
                .retry(cacheLock.retry())
                .sleep(cacheLock.sleep())
                .build();
    }

    private static class Builder extends AbsCacheOperation.Builder<CacheLockOperation> {

        @Override
        public CacheLockOperation build() {
            return new CacheLockOperation(this);
        }
    }
}
