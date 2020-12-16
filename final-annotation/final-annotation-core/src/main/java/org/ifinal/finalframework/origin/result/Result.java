package org.ifinal.finalframework.origin.result;

import java.io.Serializable;
import java.time.Duration;
import java.util.Locale;
import java.util.TimeZone;
import lombok.Getter;
import lombok.Setter;
import org.ifinal.finalframework.origin.IData;
import org.ifinal.finalframework.origin.IException;
import org.ifinal.finalframework.origin.IResult;
import org.ifinal.finalframework.origin.IUser;

/**
 * 业务数据返回结果封装，统一业务返回的数据结构。
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
public final class Result<T> implements IResult<T>, Responsible, Serializable {

    private static final long serialVersionUID = -2801752781032532754L;

    /**
     * the result {@literal status}
     *
     * @see ResponseStatus#getCode()
     */
    private Integer status;

    /**
     * the result {@literal description}
     *
     * @see ResponseStatus#getDesc()
     */
    private String description;

    /**
     * the result error {@linkplain IException#getCode() code}
     */
    private String code;

    /**
     * the result error {@linkplain IException#getMessage() message}
     */
    private String message;

    /**
     * the result {@linkplain IData#getData() data}
     */
    @SuppressWarnings("all")
    private T data;

    /**
     * the {@linkplain Pagination pagination}
     */
    private Pagination pagination;

    /**
     * trace
     */
    private String trace;

    /**
     * 响应时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 执行时长
     */
    private Duration duration;

    /**
     * 服务地址
     */
    private String address;

    /**
     * 服务IP
     */
    private String ip;

    /**
     * 国际化
     */
    private Locale locale;

    /**
     * 时区
     */
    private TimeZone timeZone;

    /**
     * 操作者
     */
    private IUser<?> operator;

    /**
     * @see com.fasterxml.jackson.annotation.JsonView
     */
    private Class<?> view;

    private Class<? extends Throwable> exception;

    public Result() {
    }

    public Result(final Integer status, final String description, final String code, final String message,
        final T data) {

        this.status = status;
        this.description = description;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(final Integer status, final String description, final String code, final String message) {

        this.status = status;
        this.description = description;
        this.code = code;
        this.message = message;
    }

    public Result(final Integer status, final String description, final T data) {

        this(status, description, status.toString(), description, data);
    }

    public Result(final Integer status, final String description) {

        this(status, description, status.toString(), description);
    }

}
