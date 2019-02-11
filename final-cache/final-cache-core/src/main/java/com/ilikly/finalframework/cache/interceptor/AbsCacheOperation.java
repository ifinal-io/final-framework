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
    private final String keyPattern;
    private final String[] keys;
    private final String fieldPattern;
    private final String[] fields;
    private final String condition;
    private final String expire;
    private final Long ttl;
    private final TimeUnit timeUnit;

    protected AbsCacheOperation(Builder builder) {
        this.keyPattern = Assert.isEmpty(builder.keyFormat) ? null : builder.keyFormat;
        this.keys = Assert.isEmpty(builder.keys) ? null : builder.keys;
        this.fieldPattern = Assert.isEmpty(builder.fieldFormat) ? null : builder.fieldFormat;
        this.fields = Assert.isEmpty(builder.fields) ? null : builder.fields;
        this.condition = Assert.isEmpty(builder.condition) ? null : builder.condition;
        this.expire = Assert.isEmpty(builder.expire) ? null : builder.expire;
        this.ttl = builder.ttl;
        this.timeUnit = builder.timeUnit;
    }

    @Override
    public String keyPattern() {
        return keyPattern;
    }

    @Override
    public String[] keys() {
        return keys;
    }

    @Override
    public String fieldPattern() {
        return fieldPattern;
    }

    @Override
    public String[] fields() {
        return fields;
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
        private String keyFormat;
        private String[] keys;
        private String fieldFormat;
        private String[] fields;
        private String condition;
        private String expire;
        private Long ttl;
        private TimeUnit timeUnit;

        public Builder<O> keyFormat(String format) {
            this.keyFormat = format;
            return this;
        }

        public Builder<O> keys(String[] keys) {
            this.keys = keys;
            return this;
        }

        public Builder<O> fieldFormat(String format) {
            this.fieldFormat = format;
            return this;
        }

        public Builder<O> fields(String[] fields) {
            this.fields = fields;
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
