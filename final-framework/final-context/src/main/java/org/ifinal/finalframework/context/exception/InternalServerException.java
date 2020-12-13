package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.annotation.core.result.ResponseStatus;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0.0
 * @see ForbiddenException
 * @see NotFoundException
 * @see BadRequestException
 * @since 1.0.0
 */
public class InternalServerException extends ServiceException {

    public static final InternalServerException DEFAULT = new InternalServerException(
        ResponseStatus.INTERNAL_SERVER_ERROR.getDesc());

    public InternalServerException(final String message, final Object... args) {

        this(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), message, args);
    }

    public InternalServerException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public InternalServerException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public InternalServerException(final String code, final String message, final Object... args) {

        super(ResponseStatus.INTERNAL_SERVER_ERROR.getCode(), ResponseStatus.INTERNAL_SERVER_ERROR.getDesc(), code,
            message, args);
    }

}
