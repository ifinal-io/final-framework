package cn.com.likly.finalframework.data.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:17
 * @since 1.0
 */
public class ServiceException extends RuntimeException implements IException {
    private final Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(IException exception) {
        this(exception.getCode(), exception.getMessage());
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
