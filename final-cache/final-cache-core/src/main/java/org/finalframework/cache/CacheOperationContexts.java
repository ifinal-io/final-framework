package org.finalframework.cache;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 10:19:48
 * @since 1.0
 */
public interface CacheOperationContexts {

    CacheConfiguration configuration();

    <O extends CacheOperation> Collection<CacheOperationContext> get(Class<O> operation);
}
