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

    public ServiceException(Integer code, String message, Object... args) {
        super(String.format(message, args));
        Assert.isNull(code, "code is null");
        this.code = code;
    }

    public ServiceException(IException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), args);
    }


    @Override
    public Integer getCode() {
        return this.code;
    }
}
