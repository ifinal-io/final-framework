package org.finalframework.monitor.action;


import org.finalframework.core.Assert;
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
 * @since 1.0
 */
public class ActionOperation implements Operation {
    private final String name;
    private final int type;
    private final int action;
    private final String operator;
    private final String target;
    private final CutPoint point;
    private final Map<String, String> attributes;
    private final Class<? extends Invocation> invocation;

    private ActionOperation(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.action = builder.action;
        this.operator = Assert.isBlank(builder.operator) ? null : builder.operator;
        this.target = builder.target;
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
