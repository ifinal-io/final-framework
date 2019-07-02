package org.finalframework.monitor.action;

import org.finalframework.spring.aop.Executor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:56:09
 * @since 1.0
 */
public interface ActionRecorder<T> extends Executor {

    void record(ActionContext<T> context);

}
