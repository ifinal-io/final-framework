package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.annotation.result.ResponseStatus;

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
