package org.ifinal.finalframework.monitor;


import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.context.exception.ServiceException;

/**
 * Action异常
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class MonitorException extends ServiceException {
    public MonitorException(Integer status, String description, IException exception, Object... args) {
        super(status, description, exception, args);
    }

    public MonitorException(Integer status, String description) {
        super(status, description);
    }

    public MonitorException(Integer status, String description, String code, String message, Object... args) {
        super(status, description, code, message, args);
    }
}
