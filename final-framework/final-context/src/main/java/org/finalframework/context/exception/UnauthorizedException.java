package org.finalframework.context.exception;

import org.finalframework.annotation.IException;
import org.finalframework.annotation.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class UnauthorizedException extends ServiceException {

    public static final UnauthorizedException DEFAULT = new UnauthorizedException(ResponseStatus.UNAUTHORIZED.getDesc());


    public UnauthorizedException(String message, Object... args) {
        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public UnauthorizedException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public UnauthorizedException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public UnauthorizedException(String code, String message, Object... args) {
        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }


}
