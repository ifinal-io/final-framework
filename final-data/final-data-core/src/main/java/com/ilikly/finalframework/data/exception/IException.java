package com.ilikly.finalframework.data.exception;

import lombok.NonNull;

/**
 * 统一异常处理接口，实现该接口的异常，将会被{@literal Spring}的异常处理机制拦截，
 * 并将异常所声明的错误码{@link #getCode()}和错误消息{@link #getMessage()}封装到{@link com.ilikly.finalframework.data.result.Result}中。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 20:57
 * @see com.ilikly.finalframework.data.result.Result
 * @since 1.0
 */
public interface IException {

    @NonNull
    Integer getCode();

    @NonNull
    String getMessage();
}
