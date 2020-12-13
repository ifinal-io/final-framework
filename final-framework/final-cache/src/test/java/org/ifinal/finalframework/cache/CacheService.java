package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.cache.annotation.Cacheable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CacheService {

    @Cacheable(key = "${key}")
    Integer cacheable(final Integer key);

}
