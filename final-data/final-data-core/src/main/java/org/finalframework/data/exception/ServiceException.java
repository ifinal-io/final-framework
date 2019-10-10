package org.finalframework.data.exception;

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
public class ServiceException extends RuntimeException implements IException {
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
    /**
     * 提示消息
     */
    private final String toast;

    public ServiceException(Integer status, String description, Integer code, String message) {
        this(status, description, code, message, null);
    }

    public ServiceException(Integer status, String description, Integer code, String message, String toast, Object... args) {
        super(message);
        this.status = status;
        this.description = description;
        this.code = code;
        this.toast = toast == null ? message : String.format(toast, args);
    }

    public ServiceException(Integer code, String message) {
        this(code, message, code, message, message);
    }

    public ServiceException(Integer code, String message, String toast, Object... args) {
        this(code, message, code, message, toast == null ? message : String.format(toast, args));
    }

    public ServiceException(IException exception, Object... args) {
        this(exception.getCode(), exception.getMessage(), exception.getToast(), args);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getToast() {
        return this.toast;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
