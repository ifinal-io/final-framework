package com.ilikly.finalframework.cache;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:47:29
 * @since 1.0
 */
public interface CacheExecutor {

    Object execute(CacheOperationInvoker invoker, Object target, Method method, Object args);

}
