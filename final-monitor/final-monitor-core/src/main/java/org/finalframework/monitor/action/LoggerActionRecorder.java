package org.finalframework.monitor.action;


import org.finalframework.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-28 12:52:19
 * @since 1.0
 */
public class LoggerActionRecorder implements ActionRecorder<Object> {
    private static final Logger logger = LoggerFactory.getLogger(LoggerActionRecorder.class);

    @Override
    public void record(ActionContext<Object> context) {
        logger.info("==> {}", Json.toJson(context));
    }
}
