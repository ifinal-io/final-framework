package org.finalframework.spring.web.handler.exception;

/**
 * 全局异常处理器
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 16:00:13
 * @since 1.0
 */
public interface GlobalExceptionHandler<T> {
    T handle(Throwable throwable) throws Throwable;
}
