package org.ifinal.finalframework.monitor.context;

import org.ifinal.finalframework.annotation.monitor.MonitorLevel;
import org.ifinal.finalframework.monitor.MonitorException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AlertContext {
    /**
     * 名称
     */
    private final String name;
    /**
     * 级别
     */
    private final MonitorLevel level;
    /**
     * 目标
     */
    private final Object target;
    /**
     * 属性
     */
    private final Map<String, Object> attributes;
    /**
     * 异常
     */
    private final MonitorException exception;
    /**
     * 追踪
     */
    private final String trace;
    /**
     * 时间戳
     */
    private final Long timestamp;

    private AlertContext(Builder builder) {
        this.name = builder.name;
        this.level = builder.level;
        this.target = builder.target;
        this.attributes = builder.attributes;
        this.exception = builder.exception;
        this.trace = builder.trace;
        this.timestamp = builder.timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public MonitorLevel getLevel() {
        return level;
    }

    public String getTrace() {
        return trace;
    }


    public Object getTarget() {
        return target;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public MonitorException getException() {
        return exception;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public static class Builder implements org.ifinal.finalframework.util.Builder<AlertContext> {
        private String name;
        private MonitorLevel level;
        private Object target;
        private final Map<String, Object> attributes = new HashMap<>();
        private MonitorException exception;
        private String trace;
        private Long timestamp;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder level(MonitorLevel level) {
            this.level = level;
            return this;
        }

        public Builder trace(String trace) {
            this.trace = trace;
            return this;
        }


        public Builder addAttribute(String name, Object value) {
            this.attributes.put(name, value);
            return this;
        }

        public Builder exception(MonitorException exception) {
            this.exception = exception;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public AlertContext build() {
            return new AlertContext(this);
        }
    }
}
