package org.finalframework.monitor.autoconfigure;


import org.finalframework.monitor.action.ActionContextHandler;
import org.finalframework.monitor.action.ActionContextLoggerHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:31:36
 * @since 1.0
 */
@ConfigurationProperties(prefix = MonitorProperties.MONITOR_PREFIX)
public class MonitorProperties {
    static final String MONITOR_PREFIX = "final.monitor";

    private ActionProperties action = new ActionProperties();

    public ActionProperties getAction() {
        return action;
    }

    public void setAction(ActionProperties action) {
        this.action = action;
    }

    public static class ActionProperties {
        private String logger = ActionContextLoggerHandler.class.getName();

        private Class<? extends ActionContextHandler> handler = ActionContextLoggerHandler.class;

        public String getLogger() {
            return logger;
        }

        public void setLogger(String logger) {
            this.logger = logger;
        }

        public Class<? extends ActionContextHandler> getHandler() {
            return handler;
        }

        public void setHandler(Class<? extends ActionContextHandler> handler) {
            this.handler = handler;
        }
    }
}
