package org.finalframework.data.exception;

import org.finalframework.core.Assert;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:17
 * @since 1.0
 */
@SuppressWarnings({"unused"})
public class ServiceException extends RuntimeException implements IException {
    private final Integer code;
    private final String toast;

    @Deprecated
    public ServiceException(Integer code, String message, Object... args) {
        super(String.format(message, args));
        Assert.isNull(code, "code is null");
        this.code = code;
        this.toast = null;
    }

    public ServiceException(Integer code, String message, String toast, Object... args) {
        super(String.format(message, args));
        Assert.isNull(code, "code is null");
        this.code = code;
        this.toast = toast == null ? null : String.format(toast, args);
    }

    public ServiceException(IException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), exception.getToast(), args);
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getToast() {
        return this.toast;
    }
}
