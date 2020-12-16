package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.origin.IException;
import org.ifinal.finalframework.origin.result.ResponseStatus;

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

    public NotFoundException(final String message, final Object... args) {

        this(ResponseStatus.NOT_FOUND.getCode(), message, args);
    }

    public NotFoundException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public NotFoundException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public NotFoundException(final String code, final String message, final Object... args) {

        super(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getDesc(), code, message, args);
    }

}
