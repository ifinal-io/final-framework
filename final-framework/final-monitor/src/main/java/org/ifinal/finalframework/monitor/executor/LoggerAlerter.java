package org.ifinal.finalframework.monitor.executor;

import org.ifinal.finalframework.monitor.context.AlertContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoggerAlerter implements Alerter {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAlerter.class);

    @Override
    public void alert(AlertContext<?> context) {

    }
}
