package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.core.annotation.IException;
import org.ifinal.finalframework.core.annotation.ResponseStatus;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0.0
 * @see ForbiddenException
 * @see NotFoundException
 * @since 1.0.0
 */
public class BadRequestException extends ServiceException {

    public static final BadRequestException DEFAULT = new BadRequestException(ResponseStatus.BAD_REQUEST.getDesc());

    public BadRequestException(final String message, final Object... args) {

        this(ResponseStatus.BAD_REQUEST.getCode(), message, args);
    }

    public BadRequestException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public BadRequestException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public BadRequestException(final String code, final String message, final Object... args) {

        super(ResponseStatus.BAD_REQUEST.getCode(), ResponseStatus.BAD_REQUEST.getDesc(), code, message, args);
    }

}
