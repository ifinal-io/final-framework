

package org.finalframework.context.exception.handler;

import org.finalframework.context.exception.result.ResultGlobalResultExceptionHandler;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 全局异常处理器，将系统中抛出的业务异常或非业务异常转化为可读的结果返回给调用者。
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 16:00:13
 * @see ResultGlobalResultExceptionHandler
 * @since 1.0
 */
public interface GlobalExceptionHandler<T> {


    /**
     * 将异常 {@link Throwable} 转化为可读的结果 {@link T}
     *
     * @param throwable 异常
     */
    @Nullable
    T handle(@NonNull Throwable throwable);
}
