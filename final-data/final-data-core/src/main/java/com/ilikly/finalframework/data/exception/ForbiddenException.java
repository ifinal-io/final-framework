package com.ilikly.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(CommonServiceException.FORBIDDEN.getMessage());

    public ForbiddenException(String message, Object... args) {
        super(CommonServiceException.FORBIDDEN.getCode(), String.format(message, args));
    }

}
