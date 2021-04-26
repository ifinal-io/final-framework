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

package org.finalframework.util.function;

import org.springframework.lang.Nullable;

import java.util.function.Function;

/**
 * @author likly
 * @version 1.0.0
 * @see org.springframework.core.convert.converter.Converter
 * @see java.util.function.Function
 * @since 1.0.0
 */
@FunctionalInterface
public interface Converter<T, R> extends Function<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param source the function argument
     * @return the function result
     */
    @Override
    @Nullable
    default R apply(final @Nullable T source) {

        return convert(source);
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} ({@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     */
    @Nullable
    R convert(@Nullable T source);

}
