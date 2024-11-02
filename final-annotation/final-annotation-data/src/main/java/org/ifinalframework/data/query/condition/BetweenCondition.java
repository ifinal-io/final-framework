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

import org.ifinalframework.data.query.BetweenValue;
import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionExpression;

/**
 * BetweenCondition.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface BetweenCondition<V> extends Condition {

    /**
     * <pre class="code">
     * BETWEEN #{min} AND #{max}
     * </pre>
     * @param min min value
     * @param max max value
     * @return between sql
     */
    default Criterion between(@Nullable V min, @Nullable V max) {
        return condition(CriterionExpression.BETWEEN, new BetweenValue<>(min, max));
    }

    /**
     * <pre class="code">
     * NOT BETWEEN #{min} AND #{max}
     * </pre>
     * @param min min value
     * @param max max value
     * @return not between sql
     */
    default Criterion notBetween(@Nullable V min, @Nullable V max) {
        return condition(CriterionExpression.NOT_BETWEEN, new BetweenValue<>(min, max));
    }

}
