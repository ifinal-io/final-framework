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

package org.finalframework.data.mapping;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 20:12:25
 * @since 1.0
 */
public class BaseCompareProperty implements CompareProperty {
    private final Property property;
    private final Object beforeValue;
    private final Object afterValue;
    private final boolean equals;

    private BaseCompareProperty(BuilderImpl builder) {
        this.property = builder.property;
        this.beforeValue = builder.beforeValue;
        this.afterValue = builder.afterValue;

        if (beforeValue == null && afterValue == null) {
            this.equals = false;
        } else if (beforeValue == null || afterValue == null) {
            this.equals = true;
        } else {
            this.equals = beforeValue.equals(afterValue);
        }

    }

    @Override
    public Property property() {
        return this.property;
    }

    @Override
    public Object beforeValue() {
        return this.beforeValue;
    }

    @Override
    public Object afterValue() {
        return this.afterValue;
    }

    @Override
    public boolean equals() {
        return this.equals;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(property.getName()).append("[beforeValue=")
                .append(beforeValue)
                .append(",afterValue=")
                .append(afterValue)
                .append(",equals=")
                .append(equals)
                .append("]");
        return builder.toString();
    }

    public static class BuilderImpl implements CompareProperty.Builder {
        private Property property;
        private Object beforeValue;
        private Object afterValue;

        @Override
        public Builder property(Property property) {
            this.property = property;
            return this;
        }

        @Override
        public Builder value(Object before, Object after) {
            this.beforeValue = before;
            this.afterValue = after;
            return this;
        }

        @Override
        public CompareProperty build() {
            return new BaseCompareProperty(this);
        }
    }
}
