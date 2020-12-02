package org.ifinal.finalframework.monitor.executor;

import org.ifinal.finalframework.aop.Executor;
import org.ifinal.finalframework.monitor.context.AlertContext;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Alerter extends Executor {
    void alert(AlertContext context);
}
