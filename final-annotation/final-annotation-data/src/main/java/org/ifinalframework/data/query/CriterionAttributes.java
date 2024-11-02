/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query;

import org.springframework.core.annotation.AnnotationAttributes;

import org.ifinalframework.data.annotation.criterion.CriterionSqlProvider;

/**
 * @author iimik
 * @version 1.0.0
 * @see CriterionSqlProvider
 * @since 1.0.0
 */
public class CriterionAttributes extends AnnotationAttributes implements Criterion {

    /**
     * andOr
     */
    public static final String ATTRIBUTE_NAME_AND_OR = "andOr";

    /**
     * property
     */
    public static final String ATTRIBUTE_NAME_PROPERTY = "property";

    /**
     * column
     */
    public static final String ATTRIBUTE_NAME_COLUMN = "column";

    /**
     * value
     */
    public static final String ATTRIBUTE_NAME_VALUE = "value";

    /**
     * javaType
     */
    public static final String ATTRIBUTE_NAME_JAVA_TYPE = "javaType";

    /**
     * typeHandler
     */
    public static final String ATTRIBUTE_NAME_TYPE_HANDLER = "typeHandler";

    /**
     * query
     */
    public static final String ATTRIBUTE_NAME_QUERY = "query";

    /**
     * expression
     */
    public static final String ATTRIBUTE_NAME_EXPRESSION = "expression";

    public AndOr getAndor() {
        return getEnum(ATTRIBUTE_NAME_AND_OR);
    }

    public String getProperty() {
        return getString(ATTRIBUTE_NAME_PROPERTY);
    }

    public String getColumn() {
        return getString(ATTRIBUTE_NAME_COLUMN);
    }

    public Object getValue() {
        return get(ATTRIBUTE_NAME_VALUE);
    }

    public Class<?> getJavaType() {
        return getClass(ATTRIBUTE_NAME_JAVA_TYPE);
    }

    public Class<?> getTypeHandler() {
        return getClass(ATTRIBUTE_NAME_TYPE_HANDLER);
    }

    public void setExpression(String expression) {
        put(ATTRIBUTE_NAME_EXPRESSION, expression);
    }

    public void setColumn(String column) {
        put(ATTRIBUTE_NAME_COLUMN, column);
    }

    public void setValue(Object value) {
        put(ATTRIBUTE_NAME_VALUE, value);
    }

    public String getExpression() {
        return getString(ATTRIBUTE_NAME_EXPRESSION);
    }

    public static Builder builder(String expression) {
        return new Builder(expression);
    }

    public static class Builder {

        private final CriterionAttributes criterion = new CriterionAttributes();

        private Builder(String expression) {
            criterion.put(ATTRIBUTE_NAME_EXPRESSION, expression);
        }

        public Builder andOr(AndOr andOr) {
            criterion.put(ATTRIBUTE_NAME_AND_OR, andOr);
            return this;
        }

        public Builder column(String column) {
            criterion.put(ATTRIBUTE_NAME_COLUMN, column);
            return this;
        }

        public Builder value(Object value) {
            criterion.put(ATTRIBUTE_NAME_VALUE, value);
            return this;
        }

        public CriterionAttributes build() {
            return criterion;
        }

    }

}

