package org.finalframework.exception;

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
public interface ExceptionHandler<E, R> {

    /**
     * 返回该异常处理器是否支持该异常，如果支持则返回 {@code true}，否则返回 {@code false}。
     *
     * @param throwable 业务方法抛出的异常
     * @return 是否可以处理该异常
     */
    boolean supports(@NonNull Throwable throwable);

    /**
     * 将异常转化成可视化的结果
     *
     * @param throwable 业务方法抛出的异常
     * @return 异常转化后的结果
     */
    @NonNull
    R handle(@NonNull E throwable);
}
