/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.context.exception;

import org.ifinalframework.context.util.Messages;
import org.ifinalframework.core.IException;
import org.ifinalframework.core.ResponseStatus;
import org.ifinalframework.core.result.Responsible;
import org.springframework.lang.NonNull;

/**
 * 业务异常
 *
 * @author ilikly
 * @version 1.0.0
 * @see NotFoundException
 * @see BadRequestException
 * @see ForbiddenException
 * @see InternalServerException
 * @see UnCatchException
 * @since 1.0.0
 */
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

    /**
     * 异常消息
     */
    private final String message;

    private final transient Object[] args;

    public ServiceException(final Integer status, final String description, final IException exception,
                            final Object... args) {

        this(status, description, exception.getCode(), exception.getMessage(), args);
    }

    public ServiceException(final Integer status, final String description) {

        this(status, description, status.toString(), description);
    }

    protected ServiceException(ResponseStatus responseStatus, String code, String message, Object... args) {
        this(responseStatus.getCode(), responseStatus.getDesc(), code, message, args);
    }

    public ServiceException(final Integer status, final String description, final String code, final String message,
                            final Object... args) {

        super(description);
        this.status = status;
        this.description = description;
        this.code = code;
        this.message = Messages.getMessage(message, message, args);
        this.args = args;
    }

    @NonNull
    @Override
    public Integer getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String getCode() {
        return this.code;
    }

    @NonNull
    @Override
    public String getMessage() {
        return this.message;
    }

    public Object[] getArgs() {
        return args;
    }

}
