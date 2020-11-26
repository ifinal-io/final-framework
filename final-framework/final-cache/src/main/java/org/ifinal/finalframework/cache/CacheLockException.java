package org.ifinal.finalframework.cache;


import org.ifinal.finalframework.context.exception.ForbiddenException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheLockException extends ForbiddenException {

    public CacheLockException(String message) {
        super(message);
    }

    public CacheLockException(Integer code, String message) {
        super(code, message);
    }
}
