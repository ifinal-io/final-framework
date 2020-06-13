/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.monitor.context;

import org.finalframework.monitor.MonitorException;
import org.finalframework.monitor.annotation.MonitorLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 18:24
 * @since 1.0
 */
public class AlertContext<T> {
    /**
     * 名称
     */
    private final String name;
    /**
     * 级别
     */
    private final MonitorLevel level;
    /**
     * 操作者
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

    private AlertContext(Builder<T> builder) {
        this.name = builder.name;
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

    public static class Builder<T> implements org.finalframework.core.Builder<AlertContext<T>> {
        private String name;
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

        public Builder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        @Override
        public AlertContext<T> build() {
            return new AlertContext<>(this);
        }
    }
}
