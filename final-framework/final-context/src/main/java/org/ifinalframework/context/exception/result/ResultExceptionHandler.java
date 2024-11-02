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

package org.ifinalframework.context.exception.result;

import org.ifinalframework.core.result.Result;
import org.ifinalframework.exception.ExceptionHandler;

/**
 * {@link Result}异常处理器，将异常所携带的异常消息封装成 {@link Result}对象返回给调用方。
 *
 * @author iimik
 * @version 1.0.0
 * @see IExceptionResultExceptionHandler
 * @see ViolationResultExceptionHandler
 * @see UnCatchResultExceptionHandler
 * @since 1.0.0
 */
public interface ResultExceptionHandler<E> extends ExceptionHandler<E, Result<?>> {

}
