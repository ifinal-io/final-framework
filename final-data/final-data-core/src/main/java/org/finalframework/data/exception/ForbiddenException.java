package org.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(CommonServiceException.FORBIDDEN.getMessage());

    public ForbiddenException(String message) {
        this(CommonServiceException.FORBIDDEN.getCode(), message);
    }

    public ForbiddenException(Integer code, String message, String toast, Object... args) {
        super(CommonServiceException.FORBIDDEN.getCode(), CommonServiceException.FORBIDDEN.getMessage(),
                code, message, toast, args);
    }

    public ForbiddenException(Integer code, String message) {
        this(code, message, message);
    }


    public ForbiddenException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), e.getToast(), args);
    }
}
