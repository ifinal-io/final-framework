package org.finalframework.data.exception;

/**
 * 未找到异常，一般为要访问的数据或页面不存在。
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see CommonServiceException#BAD_REQUEST
 * @since 1.0
 */
public class NotFoundException extends ServiceException {

    public static final NotFoundException DEFAULT = new NotFoundException(CommonServiceException.NOT_FOUND.getMessage());

    @Deprecated
    public NotFoundException(String message) {
        super(CommonServiceException.NOT_FOUND.getCode(), message);
    }

    public NotFoundException(Integer code, String message, String toast, Object... args) {
        super(code, message, toast, args);
    }

    public NotFoundException(IException exception, Object... args) {
        super(exception, args);
    }
}
