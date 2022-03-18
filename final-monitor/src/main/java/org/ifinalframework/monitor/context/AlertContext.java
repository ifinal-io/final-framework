/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.monitor.context;

import org.ifinalframework.monitor.MonitorException;
import org.ifinalframework.monitor.annotation.MonitorLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class AlertContext {

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

    private AlertContext(final Builder builder) {

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

    /**
     * Builder.
     */
    public static final class Builder implements org.ifinalframework.util.Builder<AlertContext> {

        private final Map<String, Object> attributes = new HashMap<>();

        private String name;

        private MonitorLevel level;

        private Object target;

        private MonitorException exception;

        private String trace;

        private Long timestamp;

        private Builder() {
        }

        public Builder name(final String name) {

            this.name = name;
            return this;
        }

        public Builder level(final MonitorLevel level) {

            this.level = level;
            return this;
        }

        public Builder trace(final String trace) {

            this.trace = trace;
            return this;
        }

        public Builder addAttribute(final String name, final Object value) {

            this.attributes.put(name, value);
            return this;
        }

        public Builder exception(final MonitorException exception) {

            this.exception = exception;
            return this;
        }

        public Builder timestamp(final Long timestamp) {

            this.timestamp = timestamp;
            return this;
        }

        @Override
        public AlertContext build() {
            return new AlertContext(this);
        }

    }

}
