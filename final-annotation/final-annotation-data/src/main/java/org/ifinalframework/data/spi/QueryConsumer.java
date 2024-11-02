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

import org.springframework.lang.NonNull;

/**
 * 查询消费者.
 * <p>
 * 开发者可以对用户的请求参数进行加工处理。
 *
 * @author iimik
 * @version 1.6.0
 * @see org.ifinalframework.core.IView.List
 * @since 1.6.0
 */
@FunctionalInterface
public interface QueryConsumer<Q, U> {
    /**
     * 对用户的请求参数发进行加工处理
     *
     * @param query 用户查询参数
     * @param user  当前操作用户
     */
    void accept(@NonNull SpiAction action, @NonNull Q query, @NonNull U user);
}
