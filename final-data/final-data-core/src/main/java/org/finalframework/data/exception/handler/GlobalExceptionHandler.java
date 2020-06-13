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

import org.finalframework.data.exception.result.ResultGlobalResultExceptionHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 全局异常处理器，将系统中抛出的业务异常或非业务异常转化为可读的结果返回给调用者。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 16:00:13
 * @see ResultGlobalResultExceptionHandler
 * @since 1.0
 */
public interface GlobalExceptionHandler<T> {


    /**
     * 将异常 {@link Throwable} 转化为可读的结果 {@link T}
     *
     * @param throwable 异常
     */
    @Nullable
    T handle(@NonNull Throwable throwable);
}
