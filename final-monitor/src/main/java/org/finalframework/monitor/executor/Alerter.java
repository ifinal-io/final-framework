

package org.finalframework.monitor.executor;

import org.finalframework.monitor.context.AlertContext;
import org.finalframework.spring.aop.Executor;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-10 18:17
 * @since 1.0
 */
public interface Alerter extends Executor {
    void alert(AlertContext<?> context);
}
