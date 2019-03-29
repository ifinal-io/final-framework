package org.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(CommonServiceException.FORBIDDEN.getMessage());

    @Deprecated
    public ForbiddenException(String message, Object... args) {
        super(CommonServiceException.FORBIDDEN.getCode(), String.format(message, args));
    }

    public ForbiddenException(Integer code, String message, String toast, Object... args) {
        super(code, message, toast, args);
    }

    public ForbiddenException(IException exception, Object... args) {
        super(exception, args);
    }
}
