package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.annotation.core.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class UnauthorizedException extends ServiceException {

    public static final UnauthorizedException DEFAULT = new UnauthorizedException(ResponseStatus.UNAUTHORIZED.getDesc());


    public UnauthorizedException(final String message, final Object... args) {

        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public UnauthorizedException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public UnauthorizedException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public UnauthorizedException(final String code, final String message, final Object... args) {

        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }


}
