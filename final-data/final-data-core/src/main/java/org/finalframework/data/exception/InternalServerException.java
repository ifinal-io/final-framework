package org.finalframework.data.exception;

/**
 * 错误的请求异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ForbiddenException
 * @see NotFoundException
 * @since 1.0
 */
public class InternalServerException extends ServiceException {

    public static final InternalServerException DEFAULT = new InternalServerException(CommonServiceException.INTERNAL_SERVER_ERROR.getMessage());

    public InternalServerException(String message) {
        this(CommonServiceException.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public InternalServerException(Integer code, String message) {
        this(code, message, message);
    }

    public InternalServerException(Integer code, String message, String toast, Object... args) {
        super(CommonServiceException.INTERNAL_SERVER_ERROR.getCode(), CommonServiceException.INTERNAL_SERVER_ERROR.getMessage(),
                code, message, toast, args);
    }


    public InternalServerException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), e.getToast(), args);
    }
}
