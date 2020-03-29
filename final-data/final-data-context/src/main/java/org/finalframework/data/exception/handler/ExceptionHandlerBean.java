package org.finalframework.data.exception.handler;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:15
 * @since 1.0
 */
public class ExceptionHandlerBean<R> implements ExceptionHandler<Throwable, R>, Comparable<ExceptionHandlerBean<R>> {

    private final Integer order;
    private final ExceptionHandler<Throwable, R> exceptionHandler;

    ExceptionHandlerBean(Integer order, ExceptionHandler<Throwable, R> exceptionHandler) {
        this.order = order;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public int compareTo(ExceptionHandlerBean o) {
        return this.order.compareTo(o.order);
    }

    @Override
    public boolean supports(Throwable t) {
        return exceptionHandler.supports(t);
    }

    @Override
    public R handle(Throwable throwable) {
        return exceptionHandler.handle(throwable);
    }
}
