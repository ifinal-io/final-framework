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
public class BadRequestException extends ServiceException {

    public static final BadRequestException DEFAULT = new BadRequestException(CommonServiceException.BAD_REQUEST.getMessage());

    public BadRequestException(String message) {
        this(CommonServiceException.BAD_REQUEST.getCode(), message);
    }

    public BadRequestException(Integer code, String message, String toast, Object... args) {
        super(CommonServiceException.BAD_REQUEST.getCode(), CommonServiceException.BAD_REQUEST.getMessage(),
                code, message, toast, args);
    }

    public BadRequestException(Integer code, String message) {
        this(code, message, message);
    }


    public BadRequestException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), e.getToast(), args);
    }
}
