package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.core.Assert;

import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:35:59
 * @since 1.0
 */
public abstract class AbsCacheOperation<A extends Annotation> implements CacheOperation<A> {
    private final String[] key;
    private final String[] field;
    private final String delimiter;
    private final String condition;
    private final String expire;
    private final Long ttl;
    private final TimeUnit timeUnit;

    protected AbsCacheOperation(Builder builder) {
        this.key = Assert.isEmpty(builder.key) ? null : builder.key;
        this.field = Assert.isEmpty(builder.field) ? null : builder.field;
        this.delimiter = Assert.isEmpty(builder.delimiter) ? ":" : builder.delimiter;
        this.condition = Assert.isEmpty(builder.condition) ? null : builder.condition;
        this.expire = Assert.isEmpty(builder.expire) ? null : builder.expire;
        this.ttl = builder.ttl;
        this.timeUnit = builder.timeUnit;
    }


    @Override
    public String[] key() {
        return key;
    }

    @Override
    public String[] field() {
        return field;
    }

    @Override
    public String delimiter() {
        return delimiter;
    }

    @Override
    public String condition() {
        return condition;
    }

    @Override
    public String expire() {
        return this.expire;
    }

    @Override
    public Long ttl() {
        return this.ttl;
    }

    @Override
    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    protected abstract static class Builder<O extends AbsCacheOperation> implements com.ilikly.finalframework.core.Builder<O> {
        private String[] key;
        private String delimiter;
        private String[] field;
        private String condition;
        private String expire;
        private Long ttl;
        private TimeUnit timeUnit;

        public Builder<O> key(String[] key) {
            this.key = key;
            return this;
        }

        public Builder<O> field(String[] field) {
            this.field = field;
            return this;
        }

        public Builder<O> delimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder<O> condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder<O> expire(String expire) {
            this.expire = expire;
            return this;
        }

        public Builder<O> ttl(long ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder<O> timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

    }

}
