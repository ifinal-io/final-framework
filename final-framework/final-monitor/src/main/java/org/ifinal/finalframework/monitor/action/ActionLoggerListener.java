package org.ifinal.finalframework.monitor.action;


import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.json.Json;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class ActionLoggerListener implements ActionListener {

    @Override
    public void onAction(@NonNull Action<?> action) {
        if (logger.isInfoEnabled()) {
            logger.info("==> action handler: {}", Json.toJson(action));
        }
    }
}
