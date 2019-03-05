package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.annotation.CachePut;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CachePutOperation extends AbsCacheOperation<CachePut> {

    private CachePutOperation(Builder builder) {
        super(builder);
    }

    public static CachePutOperation from(CachePut cachePut) {
        return new Builder()
                .key(cachePut.key())
                .field(cachePut.field())
                .result(cachePut.result())
                .delimiter(cachePut.delimiter())
                .condition(cachePut.condition())
                .expire(cachePut.expire())
                .ttl(cachePut.ttl())
                .timeUnit(cachePut.timeunit())
                .build();
    }

    private static class Builder extends AbsCacheOperation.Builder<CachePutOperation> {

        @Override
        public CachePutOperation build() {
            return new CachePutOperation(this);
        }
    }
}
