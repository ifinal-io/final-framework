package org.finalframework.monitor.operation;

import org.finalframework.aop.Executor;
import org.finalframework.aop.Operation;
import org.finalframework.aop.OperationHandler;
import org.finalframework.aop.annotation.CutPoint;
import org.finalframework.monitor.annotation.MonitorAction;
import org.finalframework.monitor.annotation.MonitorLevel;
import org.finalframework.monitor.executor.Alerter;
import org.finalframework.monitor.handler.AlertOperationHandler;
import org.finalframework.util.Asserts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 16:40
 * @since 1.0
 */
public class AlertOperation implements Operation {
    /**
     * 名称
     */
    private final String name;

    private final String key;
    private final String message;

    /**
     * 操作者，优先取 {@link MonitorAction#operator()} 表达式的值，当表达式的值为 {@code null}
     */
    private final String operator;
    /**
     * 操作目标，取值为 {@link MonitorAction#target()} 表达式的值。
     */
    private final String target;
    /**
     * 动作级别
     */
    private final MonitorLevel level;

    private final String condition;
    /**
     * 切点
     */
    private final CutPoint point;
    /**
     * 属性
     */
    private final Map<String, String> attributes;
    /**
     * 调度器
     */
    private final Class<? extends OperationHandler> invocation;

    private final Class<? extends Executor> executor;

    private AlertOperation(Builder builder) {
        this.name = builder.name;
        this.key = builder.key;
        this.message = builder.message;
        this.operator = Asserts.isBlank(builder.operator) ? null : builder.operator;
        this.target = builder.target;
        this.condition = builder.condition;
        this.level = builder.level;
        this.point = builder.point;
        this.invocation = builder.invocation;
        this.executor = builder.executor;
        this.attributes = Asserts.isEmpty(builder.attributes) ? null : builder.attributes;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String name() {
        return name;
    }

    public String key() {
        return key;
    }

    public String message() {
        return message;
    }

    /**
     * 操作者
     */
    public String operator() {
        return operator;
    }

    /**
     * 操作目标
     */
    public String target() {
        return target;
    }

    public String condition() {
        return condition;
    }

    public MonitorLevel level() {
        return level;
    }

    public CutPoint point() {
        return point;
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    @Override
    public Class<? extends OperationHandler> handler() {
        return invocation;
    }

    @Override
    public Class<? extends Executor> executor() {
        return this.executor;
    }

    public static class Builder implements org.finalframework.util.Builder<AlertOperation> {
        private final Map<String, String> attributes = new HashMap<>();
        private String name;
        private String key;
        private String message;
        private String operator;
        private String target;
        private String condition;
        private MonitorLevel level;
        private CutPoint point;
        private Class<? extends OperationHandler> invocation;
        private Class<? extends Executor> executor;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }


        public Builder operator(String operator) {
            this.operator = operator;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public Builder condition(String condition) {
            this.condition = condition;
            return this;
        }


        public Builder level(MonitorLevel level) {
            this.level = level;
            return this;
        }

        public Builder point(CutPoint point) {
            this.point = point;
            return this;
        }

        public Builder addAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        public Builder handler(Class<? extends OperationHandler> handler) {
            this.invocation = handler == null || handler == OperationHandler.class ? AlertOperationHandler.class : handler;
            return this;
        }

        public Builder executor(Class<? extends Executor> executor) {
            this.executor = executor == null ? Alerter.class : executor;
            return this;
        }

        @Override
        public AlertOperation build() {
            return new AlertOperation(this);
        }
    }
}
