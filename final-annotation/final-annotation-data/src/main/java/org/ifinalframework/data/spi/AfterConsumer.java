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
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * BiAfterConsumer.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
@FunctionalInterface
public interface AfterConsumer<T, P, V, R, U> {
    void accept(@NonNull SpiAction action, @Nullable List<T> entities, @NonNull P param, @Nullable V value, @Nullable R result, @NonNull U user, @NonNull Throwable e);

    @FunctionalInterface
    interface ForEach<T, P, V, R, U> extends AfterConsumer<T, P, V, R, U> {

        default void accept(@NonNull SpiAction action, @Nullable List<T> entities, @NonNull P param, @Nullable V value, @Nullable R result, @NonNull U user, @NonNull Throwable e) {
            if (CollectionUtils.isEmpty(entities)) {
                accept(action, (T) null, param, value, result, user, e);
            } else {
                entities.forEach(item -> accept(action, item, param, value, result, user, e));
            }
        }

        void accept(@NonNull SpiAction action, @Nullable T entity, @NonNull P param, @Nullable V value, @Nullable R result, @NonNull U user, @NonNull Throwable e);
    }
}
