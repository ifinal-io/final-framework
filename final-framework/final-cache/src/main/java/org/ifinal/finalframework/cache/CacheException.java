package org.ifinal.finalframework.cache;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheException extends RuntimeException {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
