package cn.com.likly.finalframework.data.redis.parser;

import cn.com.likly.finalframework.data.redis.CacheOperation;

import java.util.concurrent.TimeUnit;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-01 10:16
 * @since 1.0
 */
public class DefaultCacheOperation implements CacheOperation {

    private final OperationType type;
    private final String key;
    private final String field;
    private final String condition;
    private final String expired;
    private final TimeUnit timeUnit;

    private DefaultCacheOperation(BaseBuilder builder) {
        this.type = builder.type;
        this.key = builder.key;
        this.field = builder.field;
        this.condition = builder.condition;
        this.expired = builder.condition;
        this.timeUnit = builder.timeUnit;
    }

    public static Builder builder() {
        return new BaseBuilder();
    }

    @Override
    public OperationType getOperation() {
        return type;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public String getExpired() {
        return expired;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }


    static class BaseBuilder implements Builder {

        private OperationType type;
        private String key;
        private String field;
        private String condition;
        private String expired;
        private TimeUnit timeUnit;


        @Override
        public Builder setOperation(OperationType operation) {
            this.type = operation;
            return this;
        }

        @Override
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        @Override
        public Builder setField(String field) {
            this.field = field;
            return this;
        }

        @Override
        public Builder setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public Builder setExpired(String expired) {
            this.expired = expired;
            return this;
        }

        @Override
        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        @Override
        public CacheOperation build() {
            return new DefaultCacheOperation(this);
        }
    }
}
