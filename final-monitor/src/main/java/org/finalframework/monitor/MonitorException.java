

package org.finalframework.monitor;


import org.finalframework.annotation.IException;

/**
 * Action异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-29 22:34:46
 * @since 1.0
 */
public class MonitorException {
    private final String code;
    private final String message;

    public MonitorException(Integer code, String message) {
        this(code.toString(), message);
    }

    public MonitorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MonitorException(IException exception) {
        this(exception.getCode(), exception.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
