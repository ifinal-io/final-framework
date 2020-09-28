package org.finalframework.annotation.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
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
 *
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public final class Result<T extends Serializable> implements IResult<T>, Responsible, Serializable {

    private static final long serialVersionUID = -2801752781032532754L;
    /**
     * 状态码，描述一次请求的状态
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String description;

    /**
     * 业务状态码
     */
    private String code;
    /**
     * 业务状态描述
     */
    private String message;
    /**
     * 业务数据
     */
    private T data;
    /**
     * 分页信息
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
