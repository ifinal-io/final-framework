package org.ifinal.finalframework.monitor.interceptor;


import org.ifinal.finalframework.annotation.IUser;
import org.ifinal.finalframework.monitor.action.Action;
import org.ifinal.finalframework.monitor.action.ActionListener;
import org.ifinal.finalframework.monitor.executor.Recorder;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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
