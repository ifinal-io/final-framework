package org.finalframework.annotation.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.finalframework.annotation.IData;
import org.finalframework.annotation.IException;
import org.finalframework.annotation.IResult;
import org.finalframework.annotation.IUser;

import java.io.Serializable;
import java.time.Duration;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 业务数据返回结果封装，统一业务返回的数据结构。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:08
 * @since 1.0
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public Result(Integer status, String description, String code, String message, T data) {
        this.status = status;
        this.description = description;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(Integer status, String description, String code, String message) {
        this.status = status;
        this.description = description;
        this.code = code;
        this.message = message;
    }

    public Result(Integer status, String description, T data) {
        this(status, description, status.toString(), description, data);
    }

    public Result(Integer status, String description) {
        this(status, description, status.toString(), description);
    }


}
