package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.cache.annotation.Cacheable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheServiceImpl implements CacheService {

    @Override
    @Cacheable(key = "${key32}")
    public Integer cacheable(final Integer key) {

        return key;
    }

}
