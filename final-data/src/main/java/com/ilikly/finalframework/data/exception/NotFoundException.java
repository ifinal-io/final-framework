package com.ilikly.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class NotFoundException extends ServiceException {

    public static final NotFoundException DEFAULT = new NotFoundException(CommonServiceException.NOT_FOUND.getMessage());

    public NotFoundException(String message, Object... args) {
        super(CommonServiceException.NOT_FOUND.getCode(), String.format(message, args));
    }

}
