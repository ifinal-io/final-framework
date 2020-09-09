

package org.finalframework.monitor.operation;


import org.finalframework.core.Asserts;
import org.finalframework.monitor.action.ActionListener;
import org.finalframework.monitor.annotation.MonitorAction;
import org.finalframework.monitor.annotation.MonitorLevel;
import org.finalframework.monitor.executor.Recorder;
import org.finalframework.monitor.handler.ActionOperationHandler;
import org.finalframework.spring.aop.Executor;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.OperationHandler;
import org.finalframework.spring.aop.annotation.CutPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:03:18
 * @see MonitorAction
 * @see ActionListener
 * @since 1.0
 */
public class ActionOperation implements Operation {
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
    private final Class<? extends OperationHandler> handler;

    private final Class<? extends Executor> executor;

    private ActionOperation(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.action = builder.action;
        this.operator = Asserts.isBlank(builder.operator) ? null : builder.operator;
        this.target = builder.target;
        this.level = builder.level;
        this.point = builder.point;
        this.handler = builder.handler;
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

    /**
     * 类型
     */
    public int type() {
        return type;
    }

    /**
     * 动作
     */
    public int action() {
        return action;
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
        return handler;
    }

    @Override
    public Class<? extends Executor> executor() {
        return this.executor;
    }

    public static class Builder implements org.finalframework.core.Builder<ActionOperation> {
        private final Map<String, String> attributes = new HashMap<>();
        private String name;
        private int type;
        private int action;
        private String operator;
        private MonitorLevel level;
        private String target;
        private CutPoint point;
        private Class<? extends OperationHandler> handler;
        private Class<? extends Executor> executor;

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


        public Builder operator(String operator) {
            this.operator = operator;
            return this;
        }


        public Builder target(String target) {
            this.target = target;
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
            this.handler = handler == null || handler == OperationHandler.class ? ActionOperationHandler.class : handler;
            return this;
        }

        public Builder executor(Class<? extends Executor> executor) {
            this.executor = executor == null ? Recorder.class : executor;
            return this;
        }

        @Override
        public ActionOperation build() {
            return new ActionOperation(this);
        }
    }

}
