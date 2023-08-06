/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.context.result;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.core.result.Result;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ilikly
 * @version 1.2.1
 * @see ResultConsumer
 * @since 1.2.1
 */
@FunctionalInterface
public interface ResultFunction extends Function<Object, Result<?>>, Predicate<Object> {

    @NonNull
    @Override
    Result<?> apply(@Nullable Object body);

    @Override
    default boolean test(@Nullable Object body) {
        return true;
    }
}
