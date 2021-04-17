package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.core.IException;
import org.ifinal.finalframework.core.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(ResponseStatus.FORBIDDEN.getDesc());

    public ForbiddenException(final String message, final Object... args) {

        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public ForbiddenException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public ForbiddenException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public ForbiddenException(final String code, final String message, final Object... args) {

        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }

}
