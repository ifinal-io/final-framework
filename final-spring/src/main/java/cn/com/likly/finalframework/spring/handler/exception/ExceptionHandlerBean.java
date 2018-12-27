package cn.com.likly.finalframework.spring.handler.exception;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:15
 * @since 1.0
 */
public class ExceptionHandlerBean implements ExceptionHandler<Throwable, Object>, Comparable<ExceptionHandlerBean> {

    private final Integer order;
    private final ExceptionHandler<Throwable, Object> exceptionHandler;

    ExceptionHandlerBean(Integer order, ExceptionHandler<Throwable, Object> exceptionHandler) {
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
    public Object handle(Throwable throwable) {
        return exceptionHandler.handle(throwable);
    }
}
