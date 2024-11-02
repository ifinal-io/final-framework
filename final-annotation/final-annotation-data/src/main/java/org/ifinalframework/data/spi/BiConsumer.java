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

import java.util.List;

/**
 * 参数化数据处理点.
 *
 * 一般可用于：
 * <ul>
 *     <li>在{@code select}之后根据查询条件{@code query}进行处理，如数据填充</li>
 * </ul>
 *
 * @author iimik
 * @version 1.5.0
 * @see Consumer
 * @since 1.5.0
 */
@FunctionalInterface
public interface BiConsumer<T, P, U> {
    /**
     * @param action   操作类型
     * @param advice   切点
     * @param entities 查询到的结果集
     * @param param    参数
     * @param user     操作用户
     */
    void accept(@NonNull SpiAction action, @NonNull SpiAction.Advice advice, @NonNull List<T> entities, @Nullable P param, @NonNull U user);

    @FunctionalInterface
    interface ForEach<T, V, U> extends BiConsumer<T, V, U> {
        @Override
        default void accept(@NonNull SpiAction action, @NonNull SpiAction.Advice advice, @NonNull List<T> entities, @Nullable V param, @NonNull U user) {
            entities.forEach(item -> accept(action, advice, item, param, user));
        }

        void accept(@NonNull SpiAction action, @NonNull SpiAction.Advice advice, @NonNull T entity, @Nullable V value, @NonNull U user);
    }

}