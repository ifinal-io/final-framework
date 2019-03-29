package org.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class NotFoundException extends ServiceException {

    public static final NotFoundException DEFAULT = new NotFoundException(CommonServiceException.NOT_FOUND.getMessage());

    @Deprecated
    public NotFoundException(String message, Object... args) {
        super(CommonServiceException.NOT_FOUND.getCode(), String.format(message, args));
    }

    public NotFoundException(Integer code, String message, String toast, Object... args) {
        super(code, message, toast, args);
    }

    public NotFoundException(IException exception, Object... args) {
        super(exception, args);
    }
}
