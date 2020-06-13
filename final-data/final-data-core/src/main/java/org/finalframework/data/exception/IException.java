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

package org.finalframework.data.exception;

import lombok.NonNull;
import org.finalframework.data.exception.handler.ExceptionHandler;
import org.finalframework.data.result.Result;

/**
 * 统一异常处理接口，实现该接口的异常，将会被{@literal Spring}的异常处理机制拦截，
 * 并将异常所声明的错误码{@link #getCode()}和错误消息{@link #getMessage()}封装到{@link Result}中。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @see Result
 * @see ExceptionHandler
 * @since 1.0
 */
public interface IException {

    @NonNull
    String getCode();

    @NonNull
    String getMessage();

}
