package org.finalframework.monitor.action;


import org.finalframework.core.Assert;
import org.finalframework.monitor.action.annotation.OperationAction;
import org.finalframework.monitor.action.invocation.ActionOperationInvocation;
import org.finalframework.spring.aop.Invocation;
import org.finalframework.spring.aop.Operation;
import org.finalframework.spring.aop.annotation.CutPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:03:18
 * @see OperationAction
 * @see ActionContextHandler
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
     * 操作者，优先取 {@link OperationAction#operator()} 表达式的值，当表达式的值为 {@code null}时，再取 {@link OperatorContext#get()}。
     */
    private final String operator;
    /**
     * 操作目标，取值为 {@link OperationAction#target()} 表达式的值。
     */
    private final String target;
    /**
     * 动作级别
     */
    private final ActionLevel level;
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
    private final Class<? extends Invocation> invocation;

    private ActionOperation(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.action = builder.action;
        this.operator = Assert.isBlank(builder.operator) ? null : builder.operator;
        this.target = builder.target;
        this.level = builder.level;
        this.point = builder.point;
        this.invocation = builder.invocation;
        this.attributes = Assert.isEmpty(builder.attributes) ? null : builder.attributes;
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

    public ActionLevel level() {
        return level;
    }

    public CutPoint point() {
        return point;
    }

    public Map<String, String> attributes() {
        return attributes;
    }

    @Override
    public Class<? extends Invocation> invocation() {
        return invocation;
    }

    public static class Builder implements org.finalframework.core.Builder<ActionOperation> {
        private final Map<String, String> attributes = new HashMap<>();
        private String name;
        private int type;
        private int action;
        private String operator;
        private ActionLevel level;
        private String target;
        private CutPoint point;
        private Class<? extends Invocation> invocation;

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

        public Builder level(ActionLevel level) {
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

        public Builder invocation(Class<? extends Invocation> invocation) {
            this.invocation = invocation == null || invocation == Invocation.class ? ActionOperationInvocation.class : invocation;
            return this;
        }

        @Override
        public ActionOperation build() {
            return new ActionOperation(this);
        }
    }

}
