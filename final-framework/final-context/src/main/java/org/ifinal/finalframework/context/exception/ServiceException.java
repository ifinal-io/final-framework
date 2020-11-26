package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.annotation.IException;
import org.ifinal.finalframework.annotation.result.Responsible;
import org.ifinal.finalframework.context.util.Messages;
import org.springframework.lang.NonNull;

/**
 * 业务异常
 *
 * @author likly
 * @version 1.0.0
 * @see NotFoundException
 * @see BadRequestException
 * @see ForbiddenException
 * @see InternalServerException
 * @see UnCatchException
 * @since 1.0.0
 */
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
    private final String code;

    private final transient Object[] args;

    private final String formatMessage;

    public ServiceException(Integer status, String description, IException exception, Object... args) {
        this(status, description, exception.getCode(), exception.getMessage(), args);
    }

    public ServiceException(Integer status, String description) {
        this(status, description, status.toString(), description);
    }

    public ServiceException(Integer status, String description, String code, String message, Object... args) {
        super(message = Messages.getMessage(message, message, args));
        this.status = status;
        this.description = description;
        this.code = code;
        this.args = args;
        this.formatMessage = message == null ? null : String.format(message, args);
    }

    @NonNull
    @Override
    public Integer getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String getCode() {
        return this.code;
    }

    @NonNull
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
