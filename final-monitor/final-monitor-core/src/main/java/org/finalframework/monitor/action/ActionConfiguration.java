package org.finalframework.monitor.action;


import org.finalframework.monitor.action.component.ActionOperationComponent;
import org.finalframework.spring.aop.OperationConfiguration;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 00:02:01
 * @since 1.0
 */
public class ActionConfiguration extends OperationConfiguration {

    {
        registerCacheComponent(new ActionOperationComponent());
        setExecutor(new LoggerActionRecorder());
    }

}
