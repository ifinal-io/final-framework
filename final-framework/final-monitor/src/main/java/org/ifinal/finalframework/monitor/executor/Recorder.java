package org.ifinal.finalframework.monitor.executor;

import org.ifinal.finalframework.annotation.IUser;
import org.ifinal.finalframework.aop.Executor;
import org.ifinal.finalframework.monitor.action.Action;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Recorder<T extends IUser> extends Executor {

    void record(Action<? extends T> context);

}
