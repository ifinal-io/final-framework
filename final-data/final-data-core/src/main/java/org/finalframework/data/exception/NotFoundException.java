package org.finalframework.data.exception;

import org.finalframework.data.response.ResponseStatus;

/**
 * 未找到异常，一般为要访问的数据或页面不存在。
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ResponseStatus#BAD_REQUEST
 * @since 1.0
 */
public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {
        this(ResponseStatus.NOT_FOUND.getCode(), message);
    }

    public NotFoundException(Integer code, String message) {
        this(code, message, null);
    }

    public NotFoundException(IException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), exception.getToast(), args);
    }

    public NotFoundException(Integer code, String message, String toast, Object... args) {
        super(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getMessage(),
                code, message, toast, args);
    }


}
