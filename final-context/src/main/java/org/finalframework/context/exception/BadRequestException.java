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

package org.finalframework.context.exception;

import org.finalframework.annotation.IException;
import org.finalframework.annotation.result.ResponseStatus;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ForbiddenException
 * @see NotFoundException
 * @since 1.0
 */
public class BadRequestException extends ServiceException {

    public static final BadRequestException DEFAULT = new BadRequestException(ResponseStatus.BAD_REQUEST.getDesc());

    public BadRequestException(String message, Object... args) {
        this(ResponseStatus.BAD_REQUEST.getCode(), message, args);
    }

    public BadRequestException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public BadRequestException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public BadRequestException(String code, String message, Object... args) {
        super(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST.getDesc(), code, message, args);
    }

}
