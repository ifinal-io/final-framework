package cn.com.likly.finalframework.cache.operation;

import cn.com.likly.finalframework.cache.annotation.Cacheable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public class CacheableOperation extends AbsCacheOperationImpl {

    CacheableOperation(Builder builder) {
        super(builder);
    }

    public CacheableOperation(Cacheable cacheable) {
        this(new Builder()
                .key(cacheable.key())
                .field(cacheable.field())
                .condition(cacheable.condition())
                .expired(cacheable.expired())
                .ttl(cacheable.ttl())
                .timeUnit(cacheable.timeunit()));
    }

    public static class Builder extends AbsCacheOperationImpl.Builder<Builder, CacheableOperation> {

        @Override
        public CacheableOperation build() {
            return new CacheableOperation(this);
        }
    }

}
