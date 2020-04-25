package org.finalframework.data.result;

import java.io.Serializable;
import java.util.Locale;
import java.util.TimeZone;
import org.finalframework.data.annotation.IUser;
import org.finalframework.data.entity.PageInfo;
import org.finalframework.data.response.ResponseStatus;
import org.finalframework.data.response.Responsible;
import org.finalframework.data.user.UserContextHolder;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 业务数据返回结果封装，统一业务返回的数据结构。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 21:08
 * @see Page
 * @since 1.0
 */
public final class Result<T> implements Responsible, Serializable {

    private static final long serialVersionUID = -2801752781032532754L;
    /**
     * 状态码，描述一次请求的状态
     *
     * @see ResponseStatus#SUCCESS
     * @see ResponseStatus#BAD_REQUEST
     * @see ResponseStatus#FORBIDDEN
     * @see ResponseStatus#NOT_FOUND
     * @see ResponseStatus#INTERNAL_SERVER_ERROR
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
    private PageInfo page;
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
    private Long duration;
    /**
     * 国际化
     *
     * @see LocaleContextHolder#getLocale()
     */
    private Locale locale;
    /**
     * 时区
     *
     * @see LocaleContextHolder#getTimeZone()
     */
    private TimeZone timeZone;
    /**
     * 操作者
     *
     * @see UserContextHolder#getUser()
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

    @Override
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }


    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public IUser<?> getOperator() {
        return operator;
    }

    public void setOperator(IUser<?> operator) {
        this.operator = operator;
    }

    public Class<?> getView() {
        return view;
    }

    public void setView(Class<?> view) {
        this.view = view;
    }

    public Class<? extends Throwable> getException() {
        return exception;
    }

    public void setException(Class<? extends Throwable> exception) {
        this.exception = exception;
    }

    public boolean isSuccess() {
        return ResponseStatus.SUCCESS.getCode().equals(status);
    }


}
