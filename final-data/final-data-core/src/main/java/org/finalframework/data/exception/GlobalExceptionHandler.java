package org.finalframework.data.exception;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 全局异常处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 16:00:13
 * @since 1.0
 */
public interface GlobalExceptionHandler<T> {

    /**
     * 注册异常处理器
     *
     * @param handler 异常处理器
     */
    void registerExceptionHandler(@NonNull ExceptionHandler<T> handler);

    /**
     * 设置未捕获的异常处理器
     *
     * @param handler 未捕获的异常处理器
     */
    void setUnCatchExceptionHandler(@NonNull ExceptionHandler<T> handler);

    @Nullable
    T handle(@NonNull Throwable throwable) throws Throwable;
}
