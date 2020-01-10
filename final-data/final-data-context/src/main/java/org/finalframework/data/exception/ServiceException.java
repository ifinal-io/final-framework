package org.finalframework.data.exception;

import org.finalframework.data.response.Responsible;
import org.finalframework.data.util.Messages;

/**
 * 业务异常
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:17
 * @see NotFoundException
 * @see BadRequestException
 * @see ForbiddenException
 * @see InternalServerException
 * @see UnCatchException
 * @since 1.0
 */
@SuppressWarnings({"unused"})
public class ServiceException extends RuntimeException implements Responsible, IException {
    /**
     * 状态
     */
    private final Integer status;
    /**
     * 状态描述
     */
    private final String description;
    /**
     * 异常码
     */
    private final Integer code;

    private final Object[] args;

    private final String formatMessage;

    public ServiceException(Integer code, String message) {
        this(code, message, code, message, message);
    }

    public ServiceException(Integer code, String message, Object... args) {
        this(code, message, code, message, args);
    }

    public ServiceException(IException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), args);
    }


    public ServiceException(Integer status, String description, Integer code, String message, Object... args) {
        super(message = Messages.getMessage(message, message, args));
        this.status = status;
        this.description = description;
        this.code = code;
        this.args = args;
        this.formatMessage = message == null ? null : String.format(message, args);
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public Object[] getArgs() {
        return args;
    }

    public String getFormatMessage() {
        return this.formatMessage;
    }
}
