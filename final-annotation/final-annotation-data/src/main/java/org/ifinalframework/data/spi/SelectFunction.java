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

package org.ifinalframework.data.spi;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * SelectAction.
 *
 * @author iimik
 * @version 1.5.1
 * @see UpdateFunction
 * @see DeleteFunction
 * @since 1.5.1
 */
@FunctionalInterface
public interface SelectFunction<P, U, R> {
    /**
     * select something with param and user.
     *
     * @param param select param.
     * @param user  select user.
     * @return something selected.
     */
    @Nullable
    R select(@NonNull P param, @NonNull U user);
}
