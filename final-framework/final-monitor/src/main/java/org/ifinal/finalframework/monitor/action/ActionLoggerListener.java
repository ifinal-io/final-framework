package org.ifinal.finalframework.monitor.action;


import org.ifinal.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ActionLoggerListener implements ActionListener {

    private static final Logger logger = LoggerFactory.getLogger(ActionLoggerListener.class);

    @Override
    public void onAction(@NonNull Action<?> action) {
        logger.info("==> action handler: {}", Json.toJson(action));
    }
}
