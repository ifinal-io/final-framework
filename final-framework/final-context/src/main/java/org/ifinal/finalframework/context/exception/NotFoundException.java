package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.annotation.core.result.ResponseStatus;

/**
 * 未找到异常，一般为要访问的数据或页面不存在。
 *
 * @author likly
 * @version 1.0.0
 * @see ResponseStatus#BAD_REQUEST
 * @since 1.0.0
 */
public class NotFoundException extends ServiceException {
    public static final NotFoundException DEFAULT = new NotFoundException(ResponseStatus.NOT_FOUND.getDesc());

    public NotFoundException(String message, Object... args) {
        this(ResponseStatus.NOT_FOUND.getCode(), message, args);
    }

    public NotFoundException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public NotFoundException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public NotFoundException(String code, String message, Object... args) {
        super(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getDesc(), code, message, args);
    }


}
