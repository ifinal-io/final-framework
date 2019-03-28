package org.finalframework.monitor.action.interceptor;


import lombok.Setter;
import org.finalframework.monitor.action.ActionContext;
import org.finalframework.monitor.action.ActionContextHandler;
import org.finalframework.monitor.action.ActionRecorder;
import org.finalframework.monitor.action.annotation.ActionHandler;
import org.finalframework.spring.util.BeanUtils;
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
public class DefaultActionRecorder implements ActionRecorder, ApplicationContextAware {
    private final Map<Integer, ActionContextHandler> handlers = new ConcurrentHashMap<>(8);
    private ApplicationContext applicationContext;
    @Setter
    private ActionContextHandler defaultActionContextHandler;

    public void registerActionContextHandler(int type, ActionContextHandler handler) {
        handlers.put(type, handler);
    }

    @PostConstruct
    public void init() {
        BeanUtils.findAllBeansAnnotatedBy(applicationContext, ActionHandler.class)
                .map(it -> {
                    if (!(it instanceof ActionContextHandler)) {
                        throw new IllegalStateException("the exception handler must implements ExceptionHandler!");
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
}
