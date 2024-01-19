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

package org.ifinalframework.util.function;

import java.util.function.Predicate;

/**
 * 数据过滤器，判断数据是否匹配，用于过滤数据集中不符合目标数据的数据。
 *
 * @author iimik
 * @version 1.0.0
 * @see java.util.function.Predicate
 * @since 1.0.0
 */
@FunctionalInterface
public interface Filter<T> extends Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param data the input argument
     * @return {@code true} if the input argument matches the predicate, otherwise {@code false}
     */
    @Override
    default boolean test(T data) {
        return matches(data);
    }

    /**
     * return true if the test data {@link T} is matches, else return false.
     *
     * @param data the data to test
     * @return {@code true} if the test data {@link T} is matches, else return {@code false}.
     */
    boolean matches(T data);

}
