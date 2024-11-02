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

package org.ifinalframework.data.spi;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * AfterThrowingConsumer.
 *
 * @author iimik
 * @version 1.5.0
 * @see AfterThrowingConsumer
 * @see BiAfterReturningConsumer
 * @since 1.5.0
 */
@FunctionalInterface
public interface AfterReturningConsumer<T, R, U> {

    void accept(@NonNull SpiAction action, @NonNull List<T> entities, @Nullable R result, @NonNull U user, @Nullable Throwable throwable);

    @FunctionalInterface
    interface ForEach<T, R, U> extends AfterReturningConsumer<T, R, U> {
        /**
         * @param action   the spi action.
         * @param entities the entities will be deleted, maybe empty.
         * @param user     operator user.
         */
        default void accept(@NonNull SpiAction action, @NonNull List<T> entities, @Nullable R result, @NonNull U user, @Nullable Throwable throwable) {
            entities.forEach(item -> accept(action, item, result, user, throwable));
        }

        /**
         * @param action the spi action.
         * @param entity the entity will be deleted.
         * @param user   operator user.
         */
        void accept(@NonNull SpiAction action, @NonNull T entity, @Nullable R result, @NonNull U user, @Nullable Throwable throwable);
    }
}
