package org.finalframework.cache;

import org.finalframework.cache.component.*;
import org.finalframework.spring.aop.OperationConfiguration;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:35:22
 * @since 1.0
 */
public class CacheConfiguration extends OperationConfiguration {

    {
        registerCacheComponent(new CacheDelComponent());
        registerCacheComponent(new CacheableComponent());
        registerCacheComponent(new CacheValueComponent());
        registerCacheComponent(new CacheIncrementComponent());
        registerCacheComponent(new CachePutComponent());
        registerCacheComponent(new CacheDelComponent());
    }

}
