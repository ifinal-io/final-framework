package org.finalframework.monitor.executor;

import org.finalframework.monitor.context.AlertContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-03 10:59:34
 * @since 1.0
 */
public class LoggerAlerter implements Alerter<Object> {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAlerter.class);

    @Override
    public void alert(AlertContext<Object> context) {

    }
}
