package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.core.annotation.IException;
import org.ifinal.finalframework.core.annotation.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(ResponseStatus.FORBIDDEN.getDesc());


    public ForbiddenException(String message, Object... args) {
        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public ForbiddenException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public ForbiddenException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public ForbiddenException(String code, String message, Object... args) {
        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }


}
