package com.ilikly.finalframework.spring.aop.monitor;

import java.lang.reflect.Method;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 09:59:50
 * @since 1.0
 */
public interface OperationContext {
    Object target();
    Method method();

}
