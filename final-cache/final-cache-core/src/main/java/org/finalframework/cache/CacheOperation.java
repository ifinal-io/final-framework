package org.finalframework.cache;

import org.finalframework.spring.aop.Operation;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:12:47
 * @since 1.0
 */
public interface CacheOperation extends Operation {

    Class<? extends CacheInvocation> invocation();

    Class<? extends Cache> executor();

}
