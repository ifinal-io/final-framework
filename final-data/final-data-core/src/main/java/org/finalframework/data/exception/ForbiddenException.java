package org.finalframework.data.exception;

import org.finalframework.data.response.ResponseStatus;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(ResponseStatus.FORBIDDEN.getMessage());

    public ForbiddenException(String message) {
        this(ResponseStatus.FORBIDDEN.getCode(), message);
    }

    public ForbiddenException(Integer code, String message, String toast, Object... args) {
        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getMessage(),
                code, message, toast, args);
    }

    public ForbiddenException(Integer code, String message) {
        this(code, message, message);
    }


    public ForbiddenException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), e.getToast(), args);
    }
}
