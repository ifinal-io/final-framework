package com.ilikly.finalframework.cache;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:45:10
 * @since 1.0
 */
@FunctionalInterface
public interface CacheOperationInvoker {

    Object invoke() throws CacheException;

}
