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

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.operation.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 13:42:22
 * @since 1.0
 */
public abstract class SimpleCriterionImpl<T> implements SimpleCriterion {

    private final Object target;
    private final Operation operation;

    @SuppressWarnings("unchecked")
    public SimpleCriterionImpl(AbsBuilder builder) {
        this.target = builder.target;
        this.operation = builder.operation;
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
        return ((CriterionValueImpl) getTarget()).getSql();
    }


    @SuppressWarnings("unchecked")
    public static abstract class AbsBuilder<T, R extends Builder> implements Builder<T, R> {
        private Object target;
        private Operation operation;
        private Class<? extends TypeHandler<?>> typeHandler;

        @Override
        public R target(Object target) {
            this.target = target;
            return (R) this;
        }


        @Override
        public R operation(Operation operation) {
            this.operation = operation;
            return (R) this;
        }

    }
}
