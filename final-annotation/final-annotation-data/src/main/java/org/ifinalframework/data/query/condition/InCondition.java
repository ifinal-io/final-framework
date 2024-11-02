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

package org.ifinalframework.data.query.condition;

import org.springframework.lang.Nullable;

import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionExpression;

import java.util.Collection;

import org.ifinalframework.data.annotation.criterion.In;
import org.ifinalframework.data.annotation.criterion.NotIn;

/**
 * InCondition.
 *
 * @author iimik
 * @version 1.0.0
 * @see In
 * @see NotIn
 * @since 1.0.0
 */
public interface InCondition extends Condition {

    /**
     * @param value value
     * @return criterion
     * @see In
     */
    default Criterion in(@Nullable Collection<?> value) {
        return condition(CriterionExpression.IN, value);
    }

    /**
     * @param value value
     * @return criterion
     * @see NotIn
     */
    default Criterion nin(@Nullable Collection<?> value) {
        return condition(CriterionExpression.NOT_IN, value);
    }

}
