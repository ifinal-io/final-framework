package com.ilikly.finalframework.cache;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-05 13:14:54
 * @since 1.0
 */
public class CacheException extends RuntimeException {

    public CacheException(String message) {
        super(message);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
