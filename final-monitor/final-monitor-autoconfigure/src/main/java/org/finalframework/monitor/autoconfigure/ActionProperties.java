package org.finalframework.monitor.autoconfigure;


import org.finalframework.monitor.action.ActionContextLoggerHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 13:31:36
 * @since 1.0
 */
@ConfigurationProperties(prefix = ActionProperties.ACTION_PREFIX)
public class ActionProperties {
    static final String ACTION_PREFIX = "final.monitor.action";

    private String logger = ActionContextLoggerHandler.class.getName();

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }
}
