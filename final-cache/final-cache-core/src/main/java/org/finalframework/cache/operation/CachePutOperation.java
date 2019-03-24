package org.finalframework.cache.operation;

import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperation;
import org.finalframework.cache.annotation.CachePut;
import org.finalframework.cache.invocation.CachePutInvocation;
import org.finalframework.core.Assert;
import org.finalframework.spring.aop.annotation.CutPoint;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-10 22:52:34
 * @see CachePut
 * @since 1.0
 */
public class CachePutOperation implements CacheOperation {
    private final String name;
    private final Collection<String> key;
    private final Collection<String> field;
    private final String value;
    private final String delimiter;
    private final String condition;
    private final CutPoint cutPoint;
    private final String expire;
    private final Long ttl;
    private final TimeUnit timeUnit;
    private final Integer retry;
    private final Long sleep;
    private final Class<? extends CacheInvocation> invocation;

    private CachePutOperation(Builder builder) {
        this.name = Assert.isBlank(builder.name) ? getClass().getSimpleName() : builder.name;
        this.key = Assert.isEmpty(builder.key) ? null : builder.key;
        this.field = Assert.isEmpty(builder.field) ? null : builder.field;
        this.delimiter = builder.delimiter;
        this.value = Assert.isEmpty(builder.value) ? null : builder.delimiter;
        this.condition = Assert.isEmpty(builder.condition) ? null : builder.condition;
        this.cutPoint = builder.cutPoint;
        this.expire = Assert.isEmpty(builder.expire) ? null : builder.expire;
        this.ttl = builder.ttl;
        this.timeUnit = builder.timeUnit;
        this.retry = Assert.nonNull(builder.retry) && builder.retry > 0 ? builder.retry : null;
        this.sleep = Assert.nonNull(builder.sleep) && builder.sleep > 0L ? builder.sleep : null;
        this.invocation = builder.invocation;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String name() {
        return name;
    }

    @NonNull
    public Collection<String> key() {
        return key;
    }

    @Nullable
    public Collection<String> field() {
        return field;
    }

    @NonNull
    public String delimiter() {
        return delimiter;
    }

    @Nullable
    public String value() {
        return value;
    }

    @Nullable
    public String condition() {
        return condition;
    }

    @NonNull
    public CutPoint point() {
        return this.cutPoint;
    }

    @Nullable
    public String expire() {
        return this.expire;
    }

    @Nullable
    public Long ttl() {
        return this.ttl;
    }

    public TimeUnit timeUnit() {
        return this.timeUnit;
    }

    @Nullable
    public Integer retry() {
        return this.retry;
    }

    @Nullable
    public Long sleep() {
        return this.sleep;
    }


    @Override
    public Class<? extends CacheInvocation> invocation() {
        return invocation;
    }

    public static class Builder implements org.finalframework.core.Builder<CachePutOperation> {
        private String name;
        private Collection<String> key;
        private Collection<String> field;
        private String value;
        private String delimiter;
        private String condition;
        private CutPoint cutPoint;
        private String expire;
        private Long ttl;
        private TimeUnit timeUnit;
        private Integer retry;
        private Long sleep;
        private Class<? extends CacheInvocation> invocation;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder key(Collection<String> key) {
            this.key = key;
            return this;
        }

        public Builder field(Collection<String> field) {
            this.field = field;
            return this;
        }

        public Builder delimiter(String delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder point(CutPoint cutPoint) {
            this.cutPoint = cutPoint;
            return this;
        }

        public Builder expire(String expire) {
            this.expire = expire;
            return this;
        }

        public Builder ttl(Long ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder timeunit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder retry(Integer retry) {
            this.retry = retry;
            return this;
        }

        public Builder sleep(Long sleep) {
            this.sleep = sleep;
            return this;
        }

        public Builder invocation(Class<? extends CacheInvocation> invocation) {
            this.invocation = invocation == null || invocation == CacheInvocation.class ? CachePutInvocation.class : invocation;
            return this;
        }

        @Override
        public CachePutOperation build() {
            return new CachePutOperation(this);
        }
    }
}
