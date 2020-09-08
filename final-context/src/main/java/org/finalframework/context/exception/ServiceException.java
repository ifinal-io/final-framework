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
import org.finalframework.annotation.result.Responsible;
import org.finalframework.context.util.Messages;

/**
 * 业务异常
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:17
 * @see NotFoundException
 * @see BadRequestException
 * @see ForbiddenException
 * @see InternalServerException
 * @see UnCatchException
 * @since 1.0
 */
@SuppressWarnings({"unused"})
public class ServiceException extends RuntimeException implements Responsible, IException {
    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态描述
     */
    private final String description;
    /**
     * 异常码
     */
    private final String code;

    private final Object[] args;

    private final String formatMessage;

    public ServiceException(Integer status, String description, IException exception, Object... args) {
        this(status, description, exception.getCode(), exception.getMessage(), args);
    }

    public ServiceException(Integer status, String description) {
        this(status, description, status.toString(), description);
    }

    public ServiceException(Integer status, String description, String code, String message, Object... args) {
        super(message = Messages.getMessage(message, message, args));
        this.status = status;
        this.description = description;
        this.code = code;
        this.args = args;
        this.formatMessage = message == null ? null : String.format(message, args);
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public Object[] getArgs() {
        return args;
    }

    public String getFormatMessage() {
        return this.formatMessage;
    }
}
