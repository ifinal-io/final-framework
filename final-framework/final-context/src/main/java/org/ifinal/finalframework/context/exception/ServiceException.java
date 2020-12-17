package org.ifinal.finalframework.context.exception;

import org.ifinal.finalframework.context.util.Messages;
import org.ifinal.finalframework.annotation.core.IException;
import org.ifinal.finalframework.annotation.core.result.Responsible;
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

    private final String message;

    private final transient Object[] args;

    public ServiceException(final Integer status, final String description, final IException exception,
        final Object... args) {

        this(status, description, exception.getCode(), exception.getMessage(), args);
    }

    public ServiceException(final Integer status, final String description) {

        this(status, description, status.toString(), description);
    }

    public ServiceException(final Integer status, final String description, final String code, final String message,
        final Object... args) {

        super(description);
        this.status = status;
        this.description = description;
        this.code = code;
        this.message = Messages.getMessage(message, message, args);
        this.args = args;
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
        return this.message;
    }

    public Object[] getArgs() {
        return args;
    }

}
