

package org.finalframework.cache;


import org.finalframework.context.exception.ForbiddenException;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 03:18:19
 * @since 1.0
 */
public class CacheLockException extends ForbiddenException {

    public CacheLockException(String message) {
        super(message);
    }

    public CacheLockException(Integer code, String message) {
        super(code, message);
    }
}
