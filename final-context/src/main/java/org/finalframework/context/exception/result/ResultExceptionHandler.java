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

package org.finalframework.context.exception.result;

import org.finalframework.core.exception.ExceptionHandler;
import org.finalframework.data.annotation.result.Result;

/**
 * {@link Result}异常处理器，将异常所携带的异常消息封装成 {@link Result}对象返回给调用方。
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:14
 * @see IExceptionResultExceptionHandler
 * @see ViolationResultExceptionHandler
 * @see UnCatchResultExceptionHandler
 * @since 1.0
 */
public interface ResultExceptionHandler<E> extends ExceptionHandler<E, Result<?>> {

}
