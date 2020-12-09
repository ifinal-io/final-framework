package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.core.annotation.IException;
import org.ifinal.finalframework.core.annotation.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
