package com.ilikly.finalframework.spring.web.handler.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-23 22:06:35
 * @since 1.0
 */
public interface RestExceptionHandler<E extends Throwable, R> extends ExceptionHandler<E, R> {
}
