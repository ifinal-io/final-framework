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

package org.ifinalframework.data.query.function;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.data.query.Criteriable;
import org.ifinalframework.data.query.CriterionAttributes;

/**
 * Function.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface Function<V> {

    /**
     * @param function function
     * @return criterion
     * @since 1.3.5
     */
    default Criteriable<V> apply(@NonNull UnaryOperator<String> function) {
        return apply(function, null);
    }

    /**
     * apply function with consumer
     *
     * @param function function
     * @param consumer consumer
     * @return criterion
     */
    Criteriable<V> apply(@NonNull UnaryOperator<String> function, @Nullable Consumer<CriterionAttributes> consumer);

}
