package org.finalframework.monitor.action;


import org.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 14:12:03
 * @since 1.0
 */
public class ActionContextLoggerHandler implements ActionContextHandler<Object> {

    private final Logger logger;

    public ActionContextLoggerHandler(String logger) {
        this.logger = LoggerFactory.getLogger(logger);
    }

    @Override
    public void handle(ActionContext<Object> context) {
        logger.info("==> action invocation: {}", Json.toJson(context));
    }
}
