package cn.com.likly.finalframework.cache.operation;

import cn.com.likly.finalframework.cache.AbsCacheOperation;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:13:42
 * @since 1.0
 */
public class AbsCacheOperationImpl implements AbsCacheOperation {
    private final String key;
    private final String field;
    private final String condition;
    private final String expired;
    private final long ttl;
    private final TimeUnit timeUnit;

    protected AbsCacheOperationImpl(Builder builder) {
        this.key = builder.key;
        this.field = builder.field;
        this.condition = builder.condition;
        this.expired = builder.expired;
        this.ttl = builder.ttl;
        this.timeUnit = builder.timeUnit;
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
    public String expired() {
        return expired;
    }

    @Override
    public long ttl() {
        return ttl;
    }

    @Override
    public TimeUnit timeUnit() {
        return timeUnit;
    }

    public static abstract class Builder<B extends Builder, O extends AbsCacheOperationImpl> implements AbsCacheOperation.Builder<B, O> {
        private String key;
        private String field;
        private String condition;
        private String expired;
        private long ttl;
        private TimeUnit timeUnit;

        @Override
        public B key(String key) {
            this.key = key;
            return (B) this;
        }

        @Override
        public B field(String field) {
            this.field = field;
            return (B) this;
        }

        @Override
        public B condition(String condition) {
            this.condition = condition;
            return (B) this;
        }

        @Override
        public B expired(String expired) {
            this.expired = expired;
            return (B) this;
        }

        @Override
        public B ttl(long ttl) {
            this.ttl = ttl;
            return (B) this;
        }

        @Override
        public B timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return (B) this;
        }

    }
}
