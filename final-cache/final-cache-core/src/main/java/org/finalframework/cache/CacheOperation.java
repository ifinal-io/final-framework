package org.finalframework.cache;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:12:47
 * @since 1.0
 */
public interface CacheOperation {

    String name();

    Class<? extends CacheInvocation> invocation();


}
