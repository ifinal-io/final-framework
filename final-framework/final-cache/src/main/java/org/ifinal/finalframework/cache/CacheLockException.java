package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.context.exception.ServiceException;
import org.ifinal.finalframework.origin.IException;
import org.ifinal.finalframework.origin.result.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheLockException extends ServiceException {

    public CacheLockException(final String message, final Object... args) {

        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public CacheLockException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public CacheLockException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public CacheLockException(final String code, final String message, final Object... args) {

        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }

}
