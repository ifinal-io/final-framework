package org.ifinal.finalframework.cache;


import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.annotation.result.ResponseStatus;
import org.ifinal.finalframework.context.exception.ServiceException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheLockException extends ServiceException {

    public CacheLockException(String message, Object... args) {
        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public CacheLockException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public CacheLockException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public CacheLockException(String code, String message, Object... args) {
        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }
}
