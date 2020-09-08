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

import org.finalframework.data.annotation.IException;
import org.finalframework.data.annotation.result.ResponseStatus;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ForbiddenException
 * @see NotFoundException
 * @see BadRequestException
 * @since 1.0
 */
public class InternalServerException extends ServiceException {

    public static final InternalServerException DEFAULT = new InternalServerException(ResponseStatus.INTERNAL_SERVER_ERROR.getDesc());


    public InternalServerException(String message, Object... args) {
        this(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), message, args);
    }

    public InternalServerException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public InternalServerException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public InternalServerException(String code, String message, Object... args) {
        super(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ResponseStatus.INTERNAL_SERVER_ERROR.getDesc(), code, message, args);
    }

}
