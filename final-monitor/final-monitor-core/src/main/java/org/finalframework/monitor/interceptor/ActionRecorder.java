package org.finalframework.monitor.interceptor;


import java.util.ArrayList;
import java.util.List;
import org.finalframework.core.Assert;
import org.finalframework.monitor.action.Action;
import org.finalframework.monitor.action.ActionListener;
import org.finalframework.monitor.executor.Recorder;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Primary;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:53:45
 * @since 1.0
 */
@Primary
@SpringComponent
public class ActionRecorder implements Recorder<Object> {

    private final List<ActionListener> listeners = new ArrayList<>();

    public ActionRecorder(ObjectProvider<List<ActionListener>> handlerProvider) {
        final List<ActionListener> handlers = handlerProvider.getIfAvailable();
        if (Assert.nonEmpty(handlers)) {
            this.listeners.addAll(handlers);
        }
    }

    @Override
    public void record(Action<?> action) {
        if (Assert.nonEmpty(listeners)) {
            for (ActionListener listener : listeners) {
                listener.handle(action);
            }
        }
    }

}
