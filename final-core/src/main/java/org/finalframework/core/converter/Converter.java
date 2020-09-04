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

package org.finalframework.core.converter;

import java.util.function.Function;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 20:54:20
 * @see org.springframework.core.convert.converter.Converter
 * @see java.util.function.Function
 * @since 1.0
 */
@FunctionalInterface
@SuppressWarnings("all")
public interface Converter<T, R> extends Function<T, R>, org.springframework.core.convert.converter.Converter<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param source the function argument
     * @return the function result
     */
    @Override
    default R apply(T source) {
        return convert(source);
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     */
    @Override
    R convert(T source);
}
