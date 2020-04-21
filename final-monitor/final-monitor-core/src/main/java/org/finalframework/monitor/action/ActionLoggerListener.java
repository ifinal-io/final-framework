package org.finalframework.monitor.action;


import org.finalframework.json.Json;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 14:12:03
 * @since 1.0
 */
@SpringComponent
public class ActionLoggerListener implements ActionListener<Object> {

    private static final Logger logger = LoggerFactory.getLogger(ActionLoggerListener.class);

    @Override
    public void handle(Action<Object> action) {
        logger.info("==> action handler: {}", Json.toJson(action));
    }
}
