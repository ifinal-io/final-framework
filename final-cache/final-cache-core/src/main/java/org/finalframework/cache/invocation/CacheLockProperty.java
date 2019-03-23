package org.finalframework.cache.invocation;

import org.finalframework.cache.CacheProperty;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 00:59:48
 * @since 1.0
 */
public interface CacheLockProperty extends CacheProperty {
    boolean lock();

    Object key();

    Object value();
}
