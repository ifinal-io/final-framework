package org.finalframework.cache.operation;

import org.finalframework.cache.CacheInvocation;
import org.finalframework.cache.CacheOperation;
import org.finalframework.cache.annotation.CacheValue;
import org.finalframework.cache.invocation.CacheValueInvocation;
import org.finalframework.core.Assert;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-22 14:36:34
 * @see CacheValue
 * @since 1.0
 */
public class CacheValueOperation implements CacheOperation {
    private static final String DELIMITER = ":";
    private final String name;
    private final Collection<String> key;
    private final Collection<String> field;
    private final String delimiter;
    private final String condition;
    private final Integer index;
    private final Parameter parameter;
    private final Type parameterType;
    private final Integer retry;
    private final Long sleep;
    private final Class<? extends CacheInvocation> invocation;

    private CacheValueOperation(Builder builder) {
        this.name = Assert.isBlank(builder.name) ? getClass().getSimpleName() : builder.name;
        this.index = builder.index;
        this.parameter = builder.parameter;
        this.parameterType = builder.parameterType;
        this.key = Assert.isEmpty(builder.key) ? null : builder.key;
        this.field = Assert.isEmpty(builder.field) ? null : builder.field;
        this.delimiter = Assert.isEmpty(builder.delimiter) ? DELIMITER : builder.delimiter;
        this.condition = Assert.isEmpty(builder.condition) ? null : builder.condition;
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
    public Integer index() {
        return index;
    }

    @NonNull
    public Parameter parameter() {
        return parameter;
    }

    @NonNull
    public Type parameterType() {
        return parameterType;
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
    public String condition() {
        return condition;
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

    public static class Builder implements org.finalframework.core.Builder<CacheValueOperation> {
        private String name;
        private Collection<String> key;
        private Collection<String> field;
        private String delimiter;
        private String condition;
        private Integer index;
        private Parameter parameter;
        private Type parameterType;
        private Integer retry;
        private Long sleep;
        private Class<? extends CacheInvocation> invocation;

        private Builder() {
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder parameter(Parameter parameter) {
            this.parameter = parameter;
            return this;
        }

        public Builder parameterType(Type parameterType) {
            this.parameterType = parameterType;
            return this;
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

        public Builder condition(String condition) {
            this.condition = condition;
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
            this.invocation = invocation == null || invocation == CacheInvocation.class ? CacheValueInvocation.class : invocation;
            return this;
        }

        @Override
        public CacheValueOperation build() {
            return new CacheValueOperation(this);
        }
    }
}
