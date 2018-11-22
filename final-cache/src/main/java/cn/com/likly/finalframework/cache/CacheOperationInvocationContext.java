package cn.com.likly.finalframework.cache;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 17:46:38
 * @since 1.0
 */
public interface CacheOperationInvocationContext<O extends CacheOperation> {
    O operation();

    Object target();

    Method method();

    Object[] args();
}
