package org.finalframework.monitor.interceptor;


import org.finalframework.annotation.IUser;
import org.finalframework.monitor.action.Action;
import org.finalframework.monitor.action.ActionListener;
import org.finalframework.monitor.executor.Recorder;
import org.finalframework.util.Asserts;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:53:45
 * @since 1.0
 */
@Primary
@Component
@SuppressWarnings("rawtypes")
public class ActionRecorder implements Recorder<IUser> {

    private final List<ActionListener> listeners = new ArrayList<>();

    public ActionRecorder(ObjectProvider<List<ActionListener>> handlerProvider) {
        final List<ActionListener> handlers = handlerProvider.getIfAvailable();
        if (Asserts.nonEmpty(handlers)) {
            this.listeners.addAll(handlers);
        }
    }

    @Override
    public void record(Action<?> action) {
        if (Asserts.nonEmpty(listeners)) {
            for (ActionListener listener : listeners) {
                listener.onAction(action);
            }
        }
    }

}
