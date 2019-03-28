package org.finalframework.monitor.action.interceptor;


import org.finalframework.spring.aop.OperationConfiguration;
import org.finalframework.spring.aop.interceptor.OperationInterceptor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 12:54:28
 * @since 1.0
 */
public class ActionInterceptor extends OperationInterceptor {

    public ActionInterceptor(OperationConfiguration configuration) {
        super(configuration);
    }
}
