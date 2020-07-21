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

package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.criterion.function.SingleCriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-14 15:23:16
 * @since 1.0
 */
public interface LogicOperations {
    static CriterionFunction and(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.AND, value);
    }

    static CriterionFunction or(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.OR, value);
    }

    static CriterionFunction not(@NonNull Object target) {
        return new SimpleCriterionFunction(target, LogicOperation.NOT);
    }

    static CriterionFunction xor(@NonNull Object target, @NonNull Object value) {
        return new SingleCriterionFunction(target, LogicOperation.XOR, value);
    }
}
