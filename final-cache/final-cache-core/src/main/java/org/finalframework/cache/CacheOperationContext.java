package org.finalframework.cache;

import org.finalframework.spring.aop.OperationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 00:14:41
 * @since 1.0
 */
public interface CacheOperationContext<O extends CacheOperation> extends OperationContext<O> {

}