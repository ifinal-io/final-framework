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

package org.finalframework.data.query.criterion;


import org.finalframework.data.query.operation.JsonOperation;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 14:41:35
 * @since 1.0
 */
public class JsonContainsCriterionImpl implements JsonContainsCriterion {

    private final Object target;
    private final Operation operation;
    private final Object value;
    private final String path;

    JsonContainsCriterionImpl(Object target, Operation operation, Object value, String path) {
        this.target = target;
        this.operation = operation;
        this.value = value;
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Operation getOperation() {
        return this.operation;
    }

    public String getCriterionTarget() {
        return CriterionTarget.from(target).toString();
    }

    public String getCriterionValue() {
        final String value = this.value instanceof CriterionValue ? "criterion.value.value" : "criterion.value";
        return ((CriterionValueImpl) CriterionValue.from(this.value)).getSqlExpression(value);
    }

    @Override
    public String toString() {
        if (JsonOperation.JSON_CONTAINS == operation) {
            return String.format("JSON_CONTAINS(%s,%s,'%s')", CriterionValue.from(getTarget()), CriterionValue.from(getValue()), path);
        } else if (JsonOperation.NOT_JSON_CONTAINS == operation) {
            return String.format("!JSON_CONTAINS(%s,%s,'%s')", CriterionValue.from(getTarget()), CriterionValue.from(getValue()), path);
        }
        return null;
    }
}

