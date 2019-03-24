package org.finalframework.spring.web.exception;

import org.springframework.lang.NonNull;

/**
 * 异常处理器
 *
 * @param <R> 返回的结果
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface ExceptionHandler<R> {

    /**
     * 返回该异常处理器是否支持该异常，如果支持则返回 {@literal true}，否则返回 {@literal false}。
     *
     * @param throwable 业务异常抛出的异常
     * @return 是否可以处理该异常
     */
    boolean supports(@NonNull Throwable throwable);

    @NonNull
    R handle(@NonNull Throwable throwable);
}
