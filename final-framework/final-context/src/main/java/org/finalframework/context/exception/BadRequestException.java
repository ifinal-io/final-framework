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
