package org.ifinal.finalframework.monitor;

import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.context.exception.ServiceException;

/**
 * Action异常
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class MonitorException extends ServiceException {

    public MonitorException(final Integer status, final String description, final IException exception,
        final Object... args) {

        super(status, description, exception, args);
    }

    public MonitorException(final Integer status, final String description) {

        super(status, description);
    }

    public MonitorException(final Integer status, final String description, final String code, final String message,
        final Object... args) {

        super(status, description, code, message, args);
    }

}
