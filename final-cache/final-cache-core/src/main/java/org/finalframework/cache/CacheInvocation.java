package org.finalframework.cache;

import org.finalframework.spring.aop.Invocation;

/**
 * 调度
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-22 21:53:13
 * @since 1.0
 */
public interface CacheInvocation<O extends CacheOperation> extends Invocation<Cache, O> {
}
