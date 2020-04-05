package org.finalframework.monitor.interceptor;


import org.finalframework.data.util.Beans;
import org.finalframework.monitor.action.ActionContextHandler;
import org.finalframework.monitor.annotation.ActionHandler;
import org.finalframework.monitor.context.ActionContext;
import org.finalframework.monitor.executor.Recorder;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:53:45
 * @since 1.0
 */
@SuppressWarnings({"unchecked", "null"})
@SpringComponent
public class DefaultRecorder implements Recorder, ApplicationContextAware {
    private final Map<Integer, ActionContextHandler> handlers = new ConcurrentHashMap<>(8);
    private ApplicationContext applicationContext;
    private ActionContextHandler defaultActionContextHandler;

    public void registerActionContextHandler(int type, ActionContextHandler handler) {
        handlers.put(type, handler);
    }

    @PostConstruct
    public void init() {
        Beans.findAllBeansAnnotatedBy(applicationContext, ActionHandler.class)
                .map(it -> {
                    if (!(it instanceof ActionContextHandler)) {
                        throw new IllegalStateException("the action handler must implements ActionContextHandler!");
                    }
                    return (ActionContextHandler) it;
                }).forEach(it -> {
            final ActionHandler handler = AnnotationUtils.findAnnotation(it.getClass(), ActionHandler.class);
            for (int type : handler.value()) {
                this.registerActionContextHandler(type, it);
            }
        });

    }

    @Override
    public void record(ActionContext context) {
        ActionContextHandler handler = handlers.get(context.getType());
        if (handler == null) handler = defaultActionContextHandler;

        if (handler != null) {
            handler.handle(context);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ActionContextHandler getDefaultActionContextHandler() {
        return defaultActionContextHandler;
    }

    public void setDefaultActionContextHandler(ActionContextHandler defaultActionContextHandler) {
        this.defaultActionContextHandler = defaultActionContextHandler;
    }
}
