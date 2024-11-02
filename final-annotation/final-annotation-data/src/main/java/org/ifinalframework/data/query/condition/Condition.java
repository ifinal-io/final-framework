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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.function.Consumer;

import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionAttributes;

/**
 * A {@link Criterion} builder from {@link Condition}.
 *
 * @author iimik
 * @version 1.0.0
 * @see CompareCondition
 * @see InCondition
 * @see LikeCondition
 * @see BetweenCondition
 * @since 1.0.0
 */
@FunctionalInterface
public interface Condition {

    @NonNull
    default Criterion condition(@NonNull String expression, @Nullable Object value) {
        return condition(expression, value, null);
    }

    /**
     * @param expression criterion expression
     * @param value      criterion value
     * @param consumer   criterion consumer
     * @return a {@link Criterion} from {@link Condition}.
     */
    @NonNull
    Criterion condition(@NonNull String expression, @Nullable Object value, @Nullable Consumer<CriterionAttributes> consumer);

}
