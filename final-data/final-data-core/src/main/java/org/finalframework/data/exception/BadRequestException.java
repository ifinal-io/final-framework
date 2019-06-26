package org.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class BadRequestException extends ServiceException {

    public static final BadRequestException DEFAULT = new BadRequestException(CommonServiceException.BAD_REQUEST.getMessage());

    public BadRequestException(String message) {
        super(CommonServiceException.BAD_REQUEST.getCode(), message);
    }

    public BadRequestException(Integer code, String message, String toast, Object... args) {
        super(code, message, toast, args);
    }

    public BadRequestException(IException exception, Object... args) {
        super(exception, args);
    }
}
