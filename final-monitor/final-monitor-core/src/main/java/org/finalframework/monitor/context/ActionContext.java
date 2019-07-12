package org.finalframework.monitor.context;

import org.finalframework.monitor.MonitorException;
import org.finalframework.monitor.OperatorContext;
import org.finalframework.monitor.enums.MonitorLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Action 上下文，描述一个 Action 的动作
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:56:52
 * @since 1.0
 */
public class ActionContext<T> {
    /**
     * 名称
     */
    private final String name;
    /**
     * 类型
     */
    private final int type;
    /**
     * 动作
     */
    private final int action;
    /**
     * 级别
     */
    private final MonitorLevel level;
    /**
     * 操作者 {@link OperatorContext#get()}
     */
    private final T operator;
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

    private ActionContext(Builder<T> builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.action = builder.action;
        this.level = builder.level;
        this.operator = builder.operator;
        this.target = builder.target;
        this.attributes = builder.attributes;
        this.exception = builder.exception;
        this.trace = builder.trace;
        this.timestamp = builder.timestamp;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public int getAction() {
        return action;
    }

    public MonitorLevel getLevel() {
        return level;
    }

    public String getTrace() {
        return trace;
    }

    public T getOperator() {
        return operator;
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

    public static class Builder<T> implements org.finalframework.core.Builder<ActionContext<T>> {
        private String name;
        private int type;
        private int action;
        private MonitorLevel level;
        private T operator;
        private Object target;
        private Map<String, Object> attributes = new HashMap<>();
        private MonitorException exception;
        private String trace;
        private Long timestamp;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder action(int action) {
            this.action = action;
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

        public Builder operator(T operator) {
            this.operator = operator;
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

        public Builder target(Object target) {
            this.target = target;
            return this;
        }

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }


        @Override
        public ActionContext<T> build() {
            return new ActionContext<>(this);
        }
    }
}
