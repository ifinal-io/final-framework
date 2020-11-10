package org.finalframework.monitor.action;


import org.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 14:12:03
 * @since 1.0
 */
@Component
public class ActionLoggerListener implements ActionListener {

    private static final Logger logger = LoggerFactory.getLogger(ActionLoggerListener.class);

    @Override
    public void handle(Action<?> action) {
        logger.info("==> action handler: {}", Json.toJson(action));
    }
}
