package org.finalframework.monitor.executor;

import org.finalframework.monitor.action.Action;
import org.finalframework.spring.aop.Executor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-27 23:56:09
 * @since 1.0
 */
public interface Recorder<T> extends Executor {

    void record(Action<? extends T> context);

}
