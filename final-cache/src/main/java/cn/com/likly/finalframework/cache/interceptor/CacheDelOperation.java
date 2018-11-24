package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.CacheOperation;
import cn.com.likly.finalframework.cache.annotation.CacheDel;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheDelOperation implements CacheOperation<CacheDel> {
    private final String key;
    private final String field;
    private final String condition;

    public CacheDelOperation(CacheDel cacheDel) {
        this.key = cacheDel.key();
        this.field = cacheDel.field();
        this.condition = cacheDel.condition();
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
        throw new UnsupportedOperationException("cache del operation no support expire");
    }

    @Override
    public long ttl() {
        throw new UnsupportedOperationException("cache del operation no support ttl");
    }

    @Override
    public TimeUnit timeUnit() {
        throw new UnsupportedOperationException("cache del operation no support timeUnit");
    }
}
