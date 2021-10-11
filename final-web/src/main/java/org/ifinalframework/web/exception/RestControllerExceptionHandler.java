/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.web.exception;

import org.ifinalframework.context.exception.UnCatchException;
import org.ifinalframework.context.exception.handler.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestControllerAdvice
@ConditionalOnBean(GlobalExceptionHandler.class)
public class RestControllerExceptionHandler {

    private final GlobalExceptionHandler<?> globalExceptionHandler;

    public RestControllerExceptionHandler(final GlobalExceptionHandler<?> globalExceptionHandler) {

        this.globalExceptionHandler = globalExceptionHandler;
    }

    @ExceptionHandler
    @ResponseBody
    public Object handlerException(final Throwable throwable) {

        if (globalExceptionHandler != null) {
            return globalExceptionHandler.handle(throwable);
        }
        throw new UnCatchException(throwable);
    }

}
