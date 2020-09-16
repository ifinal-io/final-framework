package org.finalframework.context.exception;

import org.finalframework.annotation.IException;
import org.finalframework.annotation.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
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
