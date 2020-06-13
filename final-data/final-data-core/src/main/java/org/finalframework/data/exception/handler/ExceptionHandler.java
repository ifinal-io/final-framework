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

package org.finalframework.data.exception.handler;

import org.springframework.lang.NonNull;

/**
 * 异常处理器
 *
 * @param <R> 返回的结果
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface ExceptionHandler<E, R> {

    /**
     * 返回该异常处理器是否支持该异常，如果支持则返回 {@code true}，否则返回 {@code false}。
     *
     * @param throwable 业务方法抛出的异常
     * @return 是否可以处理该异常
     */
    boolean supports(@NonNull Throwable throwable);

    /**
     * 将异常转化成可视化的结果
     *
     * @param throwable 业务方法抛出的异常
     * @return 异常转化后的结果
     */
    @NonNull
    R handle(@NonNull E throwable);
}
