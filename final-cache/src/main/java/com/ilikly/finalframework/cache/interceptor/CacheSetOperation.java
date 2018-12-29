package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.cache.annotation.CacheSet;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheSetOperation implements CacheOperation<CacheSet> {
    private final String key;
    private final String field;
    private final String condition;
    private final String exprie;
    private final long ttl;
    private final TimeUnit timeUnit;

    public CacheSetOperation(CacheSet cacheSet) {
        this.key = cacheSet.key();
        this.field = cacheSet.field();
        this.condition = cacheSet.condition();
        this.exprie = cacheSet.expire();
        this.ttl = cacheSet.ttl();
        this.timeUnit = cacheSet.timeunit();
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public String field() {
        return field;
    }

    @Override
    public String condition() {
        return condition;
    }

    @Override
    public String expire() {
        return exprie;
    }

    @Override
    public long ttl() {
        return ttl;
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }
}
