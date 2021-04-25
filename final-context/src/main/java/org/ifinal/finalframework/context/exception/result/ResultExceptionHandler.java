package org.ifinal.finalframework.context.exception.result;

import org.ifinal.finalframework.core.result.Result;
import org.ifinal.finalframework.exception.ExceptionHandler;

/**
 * {@link Result}异常处理器，将异常所携带的异常消息封装成 {@link Result}对象返回给调用方。
 *
 * @author likly
 * @version 1.0.0
 * @see IExceptionResultExceptionHandler
 * @see ViolationResultExceptionHandler
 * @see UnCatchResultExceptionHandler
 * @since 1.0.0
 */
public interface ResultExceptionHandler<E> extends ExceptionHandler<E, Result<?>> {

}
