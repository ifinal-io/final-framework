package org.ifinal.finalframework.monitor.executor;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.json.Json;
import org.ifinal.finalframework.monitor.context.AlertContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class LoggerAlerter implements Alerter {

    @Override
    public void alert(AlertContext context) {
        if (logger.isWarnEnabled()) {
            logger.warn(Json.toJson(context));
        }
    }
}
