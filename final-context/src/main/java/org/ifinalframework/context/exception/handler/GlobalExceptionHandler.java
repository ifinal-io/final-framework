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

package org.ifinalframework.context.exception.handler;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinalframework.context.exception.result.ResultGlobalResultExceptionHandler;

/**
 * 全局异常处理器，将系统中抛出的业务异常或非业务异常转化为可读的结果返回给调用者。
 *
 * @author ilikly
 * @version 1.0.0
 * @see ResultGlobalResultExceptionHandler
 * @since 1.0.0
 */
public interface GlobalExceptionHandler<T> {

    @Nullable
    T handle(@NonNull Throwable throwable);

}
