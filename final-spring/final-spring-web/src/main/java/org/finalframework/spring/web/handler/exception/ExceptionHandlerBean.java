package org.finalframework.spring.web.handler.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:15
 * @since 1.0
 */
public class ExceptionHandlerBean<E extends Throwable, R> implements ExceptionHandler<E, R>, Comparable<ExceptionHandlerBean> {

    private final Integer order;
    private final ExceptionHandler<E, R> exceptionHandler;

    ExceptionHandlerBean(Integer order, ExceptionHandler<E, R> exceptionHandler) {
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
    public R handle(E throwable) {
        return exceptionHandler.handle(throwable);
    }
}
